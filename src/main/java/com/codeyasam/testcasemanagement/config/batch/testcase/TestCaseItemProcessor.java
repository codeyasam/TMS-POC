package com.codeyasam.testcasemanagement.config.batch.testcase;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.domain.BatchUpload;
import com.codeyasam.testcasemanagement.domain.TestCase;

public class TestCaseItemProcessor implements ItemProcessor<TestCase, TestCase> {

	private long batchId;
	
	public TestCaseItemProcessor(Long batchId) {
		this.batchId = batchId;
	}
	
	@Override
	public TestCase process(TestCase testCase) throws Exception {
		BatchUpload batchUpload = new BatchUpload(batchId);
		testCase.getTestCaseAttribute().setBatchUpload(batchUpload);
		return testCase;
	}

}
