package com.codeyasam.testcasemanagement.config.batch.testcase.machine;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.dto.TestCaseMachineDTO;

public class TestCaseMachineItemProcessor implements ItemProcessor<TestCase, TestCaseMachineDTO	> {

	private long machineId;
	
	public TestCaseMachineItemProcessor(long machineId) {
		this.machineId = machineId;
	}
	
	@Override
	public TestCaseMachineDTO process(TestCase item) throws Exception {
		TestCaseMachineDTO testCaseMachineDTO = new TestCaseMachineDTO();
		testCaseMachineDTO.setMachineId(machineId);
		testCaseMachineDTO.setTestCaseId(item.getId());
		return testCaseMachineDTO;
	}


}
