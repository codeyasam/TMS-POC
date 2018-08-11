package com.codeyasam.testcasemanagement.config.batch.module;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class ModuleJobCompletionListener extends JobExecutionListenerSupport {
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		super.afterJob(jobExecution);
	}
	
}
