package com.codeyasam.testcasemanagement.config.batch.application;

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

import com.codeyasam.testcasemanagement.domain.Application;

@Configuration
public class ApplicationImportConfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	
	@Autowired
	public ApplicationImportConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}
	
	@Bean(name="importApplicationJob")
	public Job importApplicationJob(ApplicationJobCompletionListener listener, @Qualifier("applicationStep1") Step step1) {
		return jobBuilderFactory.get("importApplicationJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean(name="applicationStep1")
	public Step step1(@Qualifier("applicationReader") ItemReader<Application> reader) {
		return stepBuilderFactory.get("applicationStep1")
				.<Application, Application> chunk(10)
				.reader(reader)
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean(name="applicationReader")
	@StepScope
	public FlatFileItemReader<Application> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
		FlatFileItemReader<Application> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<Application>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] { "name" });
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Application>() {{
				setTargetType(Application.class);
			}});
		}});
		reader.setLinesToSkip(1);
		return reader;
	}
	
	@Bean(name="applicationProcessor")
	@StepScope
	public ApplicationItemProcessor processor() {
		return new ApplicationItemProcessor();
	}
	
	@Bean(name="applicationWriter")
	public JdbcBatchItemWriter<Application> writer() {
		JdbcBatchItemWriter<Application> writer = new JdbcBatchItemWriter<>();
		writer.setItemPreparedStatementSetter((item, ps) -> {
			ps.setString(1, item.getName());
		});
		writer.setSql("INSERT INTO application(name) VALUES (?)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
