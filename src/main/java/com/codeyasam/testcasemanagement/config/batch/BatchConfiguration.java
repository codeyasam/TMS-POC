package com.codeyasam.testcasemanagement.config.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Bean
	public ResourcelessTransactionManager batchTransactionManager() {
		ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();
		return transactionManager;
	}
	
	@Bean
	public JobRepository jobRepository(ResourcelessTransactionManager batchTransactionManager) throws Exception {
		MapJobRepositoryFactoryBean jobRepository = new MapJobRepositoryFactoryBean();
		jobRepository.setTransactionManager(batchTransactionManager);
		return jobRepository.getObject();
	}
	
	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}
}

