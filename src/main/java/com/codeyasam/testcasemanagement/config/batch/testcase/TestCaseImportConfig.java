package com.codeyasam.testcasemanagement.config.batch.testcase;

import java.sql.PreparedStatement;

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

import com.codeyasam.testcasemanagement.domain.TestCase;

@Configuration
public class TestCaseImportConfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	
	@Autowired
	public TestCaseImportConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}
	
	@Bean(name="importTestCaseJob")
	public Job importTestCaseJob(TestCaseJobCompletionListener listener,
			@Qualifier("testCaseStep1") Step step1) {
		return jobBuilderFactory.get("importTestCaseJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean(name="testCaseStep1")
	public Step step1(@Qualifier("testCaseReader") ItemReader<TestCase> reader,
			@Qualifier("testCaseProcessor") ItemProcessor<TestCase, TestCase> processor) {
		return stepBuilderFactory.get("testCaseStep1")
				.<TestCase, TestCase> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer())
				.build();
	}
	
	@Bean(name="testCaseProcessor")
	@StepScope
	public TestCaseItemProcessor processor(TestCaseJobCompletionListener listener,
			@Value("#{jobParameters[moduleId]}") long moduleId,
			@Value("#{jobParameters[batchId]}") long batchId) {
		listener.setModuleId(moduleId);
		listener.setBatchId(batchId);
		return new TestCaseItemProcessor(batchId);
	}
	
	@Bean(name="testCaseReader")
	@StepScope
	public FlatFileItemReader<TestCase> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
		FlatFileItemReader<TestCase> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<TestCase>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"id", "name", "testCaseAttribute.description", "testCaseAttribute.location", "testCaseAttribute.priority",
						"testCaseAttribute.isSmoke", "testCaseAttribute.isMandatory"});			
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<TestCase>() {{
				setTargetType(TestCase.class);
			}});
		}});
		reader.setLinesToSkip(1);
		return reader;
	}
	
	@Bean(name="testCaseWriter")
	public JdbcBatchItemWriter<TestCase> writer() {
		JdbcBatchItemWriter<TestCase> writer = new JdbcBatchItemWriter<>();
		writer.setItemPreparedStatementSetter((TestCase item, PreparedStatement ps) -> {
			ps.setLong(1, item.getId());
			ps.setString(2, item.getName());
			ps.setString(3, item.getTestCaseAttribute().getDescription());
			ps.setString(4,  item.getTestCaseAttribute().getLocation());
			ps.setInt(5, item.getTestCaseAttribute().getPriority());
			ps.setBoolean(6, item.getTestCaseAttribute().getIsSmoke());
			ps.setBoolean(7, item.getTestCaseAttribute().getIsMandatory());
			ps.setLong(8, item.getTestCaseAttribute().getBatchUpload().getId());
		});
		writer.setSql("INSERT INTO testcase (id, name, description, location, priority, is_smoke, is_mandatory, batch_upload_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
