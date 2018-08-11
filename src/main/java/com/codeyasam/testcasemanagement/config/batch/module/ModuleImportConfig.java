package com.codeyasam.testcasemanagement.config.batch.module;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
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

import com.codeyasam.testcasemanagement.dto.ModuleApplicationDTO;

@Configuration
public class ModuleImportConfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	
	@Autowired
	public ModuleImportConfig(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}
	
	@Bean(name="importModuleJob")
	public Job importModuleJob(@Qualifier("moduleStep1") Step step1, 
			ModuleJobCompletionListener listener) {
		return jobBuilderFactory.get("importModuleJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean(name="moduleStep1")
	public Step step1(@Qualifier("moduleReader") ItemReader<ModuleApplicationDTO> reader,
			@Qualifier("moduleProcessor") ItemProcessor<ModuleApplicationDTO, ModuleApplicationDTO> processor) {
		return stepBuilderFactory.get("moduleStep1")
				.<ModuleApplicationDTO, ModuleApplicationDTO> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer())
				.build();
	}
	
	@Bean(name="moduleReader")
	@StepScope
	public FlatFileItemReader<ModuleApplicationDTO> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
		FlatFileItemReader<ModuleApplicationDTO> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<ModuleApplicationDTO>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] { "moduleName" });
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<ModuleApplicationDTO>() {{
				setTargetType(ModuleApplicationDTO.class);
			}});
		}});
		return reader;
	}
	
	@Bean(name="moduleProcessor")
	@StepScope
	public ModuleItemProcessor processor(@Value("#{jobParameters[applicationId]}") Long applicationId) {
		return new ModuleItemProcessor(applicationId);
	}
	
	@Bean(name="moduleWriter")
	public JdbcBatchItemWriter<ModuleApplicationDTO> writer() {
		JdbcBatchItemWriter<ModuleApplicationDTO> writer = new JdbcBatchItemWriter<>();
		writer.setItemPreparedStatementSetter((moduleApplicationDTO, preparedStatement) -> {
			preparedStatement.setString(1, moduleApplicationDTO.getModuleName());
			preparedStatement.setLong(2, moduleApplicationDTO.getApplicationId());
		});
		writer.setSql("INSERT INTO module (name, application_id) VALUES (?, ?)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
