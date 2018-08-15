package com.codeyasam.testcasemanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.dto.response.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.ITestCaseRetriever;
import com.codeyasam.testcasemanagement.service.TestCaseService;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {
	
	private TestCaseService testCaseService;
	
	@Autowired
	public TestCaseController(TestCaseService testCaseService) {
		this.testCaseService = testCaseService;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<TestCaseDTO> createTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.createTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully created test case.")
				.setStatus(HttpStatus.CREATED.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<TestCaseDTO> updateTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.updateTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully updated test case.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<TestCaseDTO> deleteTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.deleteTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully deleted test case.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> retrieveAll(Pageable pageable) {
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(pageable);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countAll())
				.setPrompt("Successfully retrieved all test cases.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	private List<TestCaseDTO> mapTestCaseListToDTO(Pageable pageable) {
		return testCaseService.retrieveAll(pageable)
				.stream()
				.map(testcase -> testCaseService.convertToDTO(testcase))
				.collect(Collectors.toList());
	}
	
	@RequestMapping(value="/{id}/", method=RequestMethod.GET)
	public SingleDataResponse<TestCaseDTO> searchById(@PathVariable long id) {
		TestCase testCase = testCaseService.searchById(id);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully retrieved test case by id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<TestCaseDTO> searchByName(@RequestParam("input") String name) {
		TestCase testCase = testCaseService.searchByName(name);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully retrieved test case by name.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByModuleId", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByModuleId(@RequestParam("input") long moduleId, Pageable pageable) {
		ITestCaseRetriever testCaseRetriever = (id, page) -> testCaseService.retrieveTestCasesByModuleId(moduleId, pageable);	
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseRetriever, moduleId, pageable);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByModuleId(moduleId))
				.setPrompt("Successfully retrieved test cases by module id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="searchByMachineId", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByMachineId(@RequestParam("input") long machineId, Pageable pageable) {
		ITestCaseRetriever testCaseRetriever = (id, page) -> testCaseService.retrieveTestCasesByMachineId(machineId, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseRetriever, machineId, pageable);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByMachineId(machineId))
				.setPrompt("Successfully retrieved test cases by machine id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	private List<TestCaseDTO> mapTestCaseListToDTO(ITestCaseRetriever testCaseRetriever, long id, Pageable pageable) {
		return testCaseRetriever.retrieve(id, pageable)
				.stream()
				.map(testcase -> testCaseService.convertToDTO(testcase))
				.collect(Collectors.toList());
	}
}

