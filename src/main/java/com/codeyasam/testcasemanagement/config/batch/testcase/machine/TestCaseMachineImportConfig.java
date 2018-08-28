package com.codeyasam.testcasemanagement.config.batch.testcase.machine;

import java.time.LocalDateTime;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.EnumUtils;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.domain.specification.TestCaseSpecification.SearchType;
import com.codeyasam.testcasemanagement.dto.TestCaseMachineDTO;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;
import com.codeyasam.testcasemanagement.exception.TestCaseSearchException;

@Configuration
public class TestCaseMachineImportConfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private DataSource dataSource;
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	public TestCaseMachineImportConfig(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			DataSource dataSource,
			EntityManagerFactory entityManagerFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Bean(name="importTestCaseToMachineJobByFilter")
	public Job importTestCaseToMachineJobByFilter(TestCaseMachineJobCompletionListener listener,
			@Qualifier("testCaseMachineStepByFilter") Step step) {
		return jobBuilderFactory.get("importTestCaseToMachineByFilter")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step)
				.end()
				.build();
	}
	
	@Bean(name="importTestCaseToMachineJobByModule")
	public Job importTestCaseToMachineJobByModule(TestCaseMachineJobCompletionListener listener,
			@Qualifier("testCaseMachineStepByModule") Step step) {
		return jobBuilderFactory.get("importTestCaseToMachineByModule")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step)
				.end()
				.build();
	}
	
	@Bean(name="testCaseMachineStepByFilter")
	public Step stepByFilter(@Qualifier("testCaseMachineJpaReader") ItemReader<TestCase> jpaReader,
			@Qualifier("testCaseMachineProcessor") ItemProcessor<TestCase, TestCaseMachineDTO> processor) {
		return stepBuilderFactory.get("testCaseMachineStepByFilter")
				.<TestCase, TestCaseMachineDTO> chunk(10)
				.reader(jpaReader)
				.processor(processor)
				.writer(writer())
				.build();
	}
	
	@Bean(name="testCaseMachineStepByModule")
	public Step stepByModule(@Qualifier("testCaseMachineJdbcReader") ItemReader<TestCase> jdbcReader, 
			@Qualifier("testCaseMachineProcessor") ItemProcessor<TestCase, TestCaseMachineDTO> processor) {
		return stepBuilderFactory.get("testCaseMachineStepByModule")
				.<TestCase, TestCaseMachineDTO> chunk(10)
				.reader(jdbcReader)
				.processor(processor)
				.writer(writer())
				.build();
	}
	
	@Bean(name="testCaseMachineJpaReader")
	@StepScope
	public JpaPagingItemReader<TestCase> jpaReader(@Value("#{jobParameters[text]}") String text,
			@Value("#{jobParameters[type]}") String type,
			@Value("#{jobParameters[moduleId]}") long moduleId,
			@Value("#{jobParameters[priority]}") int priority,
			@Value("#{jobParameters[isPriority]}") long isPriority,
			@Value("#{jobParameters[isMandatory]}") long isMandatory,
			@Value("#{jobParameters[isSmoke]}") long isSmoke) throws TestCaseSearchException {

		boolean hasValidSearchType = EnumUtils.isValidEnum(SearchType.class, type);
		if (!hasValidSearchType) {
			throw new TestCaseSearchException("Invalid search \"type\" in request parameters.");
		}
		
		JpaPagingItemReader<TestCase> reader = new JpaPagingItemReader<>();
		TestCaseSearchDTO testCaseSearchDTO = new TestCaseSearchDTO();
		testCaseSearchDTO.setText(text);
		testCaseSearchDTO.setType(type);
		testCaseSearchDTO.setModuleId(moduleId);
		testCaseSearchDTO.setPriority(priority);
		testCaseSearchDTO.setIsPriority(isPriority);
		testCaseSearchDTO.setIsSmoke(isSmoke);
		testCaseSearchDTO.setIsMandatory(isMandatory);
		
		JpaQueryProvider customQueryProvider = new CustomQueryProvider(testCaseSearchDTO);
		reader.setQueryProvider(customQueryProvider);
		reader.setEntityManagerFactory(entityManagerFactory);
		return reader;
	}
	
	@Bean(name="testCaseMachineJdbcReader")
	@StepScope
	public JdbcCursorItemReader<TestCase> jdbcReader(@Value("#{jobParameters[moduleId]}") Long moduleId) {
		JdbcCursorItemReader<TestCase> jdbcReader = new JdbcCursorItemReader<>();
		jdbcReader.setDataSource(dataSource);
		jdbcReader.setSql("SELECT test_cases_id as id FROM module_test_cases WHERE modules_id = ?");
		jdbcReader.setPreparedStatementSetter(preparedStatement -> {
			preparedStatement.setLong(1,  moduleId);
		});
		jdbcReader.setRowMapper(new BeanPropertyRowMapper<>(TestCase.class));
		return jdbcReader;
	}
	
	@Bean(name="testCaseMachineProcessor")
	@StepScope
	public TestCaseMachineItemProcessor processor(@Value("#{jobParameters[machineId]}") long machineId) {
		return new TestCaseMachineItemProcessor(machineId);
	}
	
	@Bean(name="testCaseMachineWriter")
	public JdbcBatchItemWriter<TestCaseMachineDTO> writer() {
		JdbcBatchItemWriter<TestCaseMachineDTO> writer = new JdbcBatchItemWriter<TestCaseMachineDTO>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TestCaseMachineDTO>());
		writer.setSql("INSERT INTO machine_test_cases (test_case_id, machine_id, created_date) VALUES (?, ?, ?)");
		writer.setItemPreparedStatementSetter((item, ps) -> {
			ps.setLong(1, item.getTestCaseId());
			ps.setLong(2, item.getMachineId());
			ps.setObject(3, LocalDateTime.now());
		});
		writer.setDataSource(dataSource);
		return writer;
	}
}
