package com.codeyasam.testcasemanagement.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.testcasemanagement.domain.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class TestCaseServiceIntegrationTest {

	@Autowired
	private TestCaseService testCaseService;
	
	@Test
	public void retrieveTestCasesByMachine() {
		long machineId = 3;
		Pageable pageable = new PageRequest(2, 2);
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByMachineId(machineId, pageable);
		Assert.assertEquals(1, testCaseList.size());
	}
	
	@Test
	public void countTestCaseByMachineId() {
		long machineId = 2;
		long testCasesCount = testCaseService.countByMacineId(machineId);
		Assert.assertEquals(2, testCasesCount);
	}
	
	@Test
	public void retrieveDistinctTestCasesByMachinesIdIn() {
		List<Long> machineIdList = Arrays.asList(2L,3L);
		Pageable pageable = new PageRequest(2, 2);
		List<TestCase> testCaseList = testCaseService.retrieveDistinctTestCaseByMachinesIdIn(machineIdList, pageable);
		Assert.assertEquals(1, testCaseList.size());
	}
	
	@Test
	public void countDistinctTestCasesByMachinesIdIn() {
		List<Long> machineIdList = Arrays.asList(2L,3L);
		long testCasesCount = testCaseService.countDistinctByMachinesIdIn(machineIdList);
		Assert.assertEquals(5, testCasesCount);
	}
	
}
