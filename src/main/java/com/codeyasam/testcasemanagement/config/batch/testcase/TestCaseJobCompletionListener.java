package com.codeyasam.testcasemanagement.config.batch.testcase;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.codeyasam.testcasemanagement.exception.BatchUploadException;

@Component
public class TestCaseJobCompletionListener extends JobExecutionListenerSupport  {
	
	private long batchId;
	private long moduleId;
	
	private JobLauncher jobLauncher;
	private Job importTestCaseToModuleJob;
	
	@Autowired
	public TestCaseJobCompletionListener(JobLauncher jobLauncher,
				@Qualifier("importTestCaseToModuleJob") Job importTestCaseToModuleJob) {
		this.jobLauncher = jobLauncher;
		this.importTestCaseToModuleJob = importTestCaseToModuleJob;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		executeImportTestCaseToModuleJob(jobExecution);
	}
	
	
	private void executeImportTestCaseToModuleJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			try {
				jobLauncher.run(importTestCaseToModuleJob, new JobParametersBuilder()
						.addLong("moduleId", moduleId)
						.addLong("batchId", batchId)
						.addLong("time", System.currentTimeMillis())
						.toJobParameters());
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException e) {
				e.printStackTrace();
				throw new BatchUploadException("Batch Upload Exception: Importing test cases to module.");
			}
		}
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
}
