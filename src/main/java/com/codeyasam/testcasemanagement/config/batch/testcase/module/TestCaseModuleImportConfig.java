package com.codeyasam.testcasemanagement.config.batch.testcase.module;

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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.codeyasam.testcasemanagement.dto.TestCaseModuleDTO;

@Configuration
public class TestCaseModuleImportConfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	
	@Autowired
	public TestCaseModuleImportConfig(JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory,
			DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}
	
	@Bean(name="importTestCaseToModuleJob")
	public Job importTestCasesToModuleJob(TestCaseModuleJobCompletionListener listener,
			@Qualifier("testCaseModuleStep") Step step) {
		return jobBuilderFactory.get("importTestCasesToModuleJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step)
				.end()
				.build();
	}
	
	@Bean(name="testCaseModuleStep")
	public Step step(@Qualifier("testCaseModuleReader") ItemReader<TestCaseModuleDTO> reader,
			@Qualifier("testCaseModuleProcessor") ItemProcessor<TestCaseModuleDTO,  TestCaseModuleDTO> processor) {
		return stepBuilderFactory.get("testCaseModuleStep")
				.<TestCaseModuleDTO, TestCaseModuleDTO> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer())
				.build();
	}
	
	@Bean(name="testCaseModuleReader")
	@StepScope
	public JdbcCursorItemReader<TestCaseModuleDTO> reader(@Value("#{jobParameters[batchId]}") long batchId) {
		JdbcCursorItemReader<TestCaseModuleDTO> databaseReader = new JdbcCursorItemReader<>();
		databaseReader.setDataSource(dataSource);
		databaseReader.setRowMapper(new BeanPropertyRowMapper<TestCaseModuleDTO>());
		databaseReader.setSql("SELECT id as testCaseId FROM testcase WHERE batch_upload_id = ?");
		databaseReader.setPreparedStatementSetter(preparedStatement -> {
			preparedStatement.setLong(1, batchId);
		});
		return databaseReader;
	}
	
	@Bean(name="testCaseModuleProcessor")
	@StepScope
	public TestCaseModuleItemProcessor processor(@Value("#{jobParameters[moduleId]}") long moduleId) {
		return new TestCaseModuleItemProcessor(moduleId);
	}	
	
	@Bean(name="testCaseModuleWriter")
	public JdbcBatchItemWriter<TestCaseModuleDTO> writer() {
		JdbcBatchItemWriter<TestCaseModuleDTO> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TestCaseModuleDTO>());
		writer.setItemPreparedStatementSetter((TestCaseModuleDTO item, PreparedStatement ps) -> {
			ps.setLong(1, item.getTestCaseId());
			ps.setLong(2, item.getModuleId());
		});
		writer.setSql("INSERT INTO module_test_cases (test_cases_id, modules_id) VALUES (?, ?)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
