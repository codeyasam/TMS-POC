package com.codeyasam.testcasemanagement.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.repository.TestCaseRepository;

@Service
public class TestCaseService {
	
	private TestCaseRepository testCaseRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public TestCaseService(TestCaseRepository testCaseRepository, ModelMapper modelMapper) {
		this.testCaseRepository = testCaseRepository;
		this.modelMapper = modelMapper;
	}
	
	public TestCase createTestCase(TestCase testCase) {
		return testCaseRepository.save(testCase);
	}
	
	public TestCase updateTestCase(TestCase testCase) {
		return testCaseRepository.save(testCase);
	}
	
	public TestCase deleteTestCase(TestCase testCase) {
		testCase = testCaseRepository.findOne(testCase.getId());
		testCaseRepository.delete(testCase);
		return testCase;
	}
	
	public List<TestCase> retrieveAll(Pageable pageable) {
		return testCaseRepository.findAll(pageable).getContent();
	}
	
	public Long countAll() {
		return testCaseRepository.count();
	}
	
	public TestCase searchById(long id) {
		return testCaseRepository.findOne(id);
	}
	
	public TestCase searchByName(String name) {
		return testCaseRepository.findByName(name);
	}
	
	public List<TestCase> retrieveTestCasesByIdIn(List<Long> idList, Pageable pageable) {
		return testCaseRepository.findByIdIn(idList, pageable).getContent();
	}
	
	public Long countByIdIn(List<Long> idList) {
		return testCaseRepository.countByIdIn(idList);
	}
	
	public List<TestCase> retrieveTestCasesByModuleId(Long id, Pageable pageable) {
		return testCaseRepository.findByModulesId(id, pageable).getContent();
	}
	
	public Long countByModuleId(Long id) {
		return testCaseRepository.countByModulesId(id);
	}
	
	public List<TestCase> retrieveTestCasesByMachineId(Long id, Pageable pageable) {
		return testCaseRepository.findByMachinesId(id, pageable).getContent();
	}
	
	public long countByMachineId(long id) {
		return testCaseRepository.countByMachinesId(id);
	}
	
	public List<TestCase> retrieveDistinctTestCaseByMachinesIdIn(List<Long> idList, Pageable pageable) {
		return testCaseRepository.findDistinctByMachinesIdIn(idList, pageable).getContent();
	}
	
	public Long countDistinctByMachinesIdIn(List<Long> idList) {
		return testCaseRepository.countDistinctByMachinesIdIn(idList);
	}
	
	public Long countDistinctBatchId() {
		return testCaseRepository.queryDistinctBatchIdCount();
	}
	
	public TestCaseDTO convertToDTO(TestCase testCase) {
		TestCaseDTO testCaseDTO = modelMapper.map(testCase, TestCaseDTO.class);
		return testCaseDTO;
	}
}
