package com.codeyasam.testcasemanagement.config.batch.machine;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.codeyasam.testcasemanagement.dto.MachineDTO;

@Configuration
public class MachineImportConfig {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	
	@Autowired
	public MachineImportConfig(JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory,
			DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}
	
	@Bean(name="importMachineJob")
	public Job importMachineJob(@Qualifier("machineStep1") Step step1, MachineJobCompletionListener listener) {
		return jobBuilderFactory.get("importMachineJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean(name="machineStep1")
	public Step step1(@Qualifier("machineReader") ItemReader<MachineDTO> reader) {
		return stepBuilderFactory.get("machineStep1")
				.<MachineDTO, MachineDTO> chunk(10)
				.reader(reader)
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean(name="machineReader")
	@StepScope
	public FlatFileItemReader<MachineDTO> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
		FlatFileItemReader<MachineDTO> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<MachineDTO>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"name"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<MachineDTO>() {{
				setTargetType(MachineDTO.class);
			}});
		}});
		return reader;
	}
	
	@Bean(name="machineProcessor")
	@StepScope
	public MachineItemProcessor processor() {
		return new MachineItemProcessor();
	}
	
	@Bean(name="machineWriter")
	public JdbcBatchItemWriter<MachineDTO> writer() {
		JdbcBatchItemWriter<MachineDTO> writer = new JdbcBatchItemWriter<>();
		writer.setItemPreparedStatementSetter((machineDTO, preparedStatement) -> {
			preparedStatement.setString(1, machineDTO.getName());
		});
		writer.setSql("INSERT INTO machine (name) VALUES (?)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
