package com.codeyasam.testcasemanagement.config.batch.testcase.module;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.dto.TestCaseModuleDTO;

public class TestCaseModuleItemProcessor implements ItemProcessor<TestCaseModuleDTO, TestCaseModuleDTO> {

	private long moduleId;
	
	public TestCaseModuleItemProcessor(long moduleId) {
		this.moduleId = moduleId;
	}
	
	@Override
	public TestCaseModuleDTO process(TestCaseModuleDTO testCaseModuleDTO) throws Exception {
		testCaseModuleDTO.setModuleId(moduleId);
		return testCaseModuleDTO;
	}

}
