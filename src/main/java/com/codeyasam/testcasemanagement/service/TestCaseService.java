package com.codeyasam.testcasemanagement.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.domain.specification.TestCaseSpecification;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;
import com.codeyasam.testcasemanagement.repository.TestCaseRepository;

@Service
public class TestCaseService {
	
	private TestCaseRepository testCaseRepository;
	private EntityManager entityManager;
	private ModelMapper modelMapper;
	
	@Autowired
	public TestCaseService(TestCaseRepository testCaseRepository,
			EntityManager entityManager,
			ModelMapper modelMapper) {
		this.testCaseRepository = testCaseRepository;
		this.entityManager = entityManager;
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
	
	public long countByModuleId(long id) {
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
	
	public List<TestCase> retrieveTestCasesByMachinesIdNotNull(Pageable pageable) {
		return testCaseRepository.findByMachinesIdNotNull(pageable).getContent();
	}
	
	public Long countByMachinesIdNotNull() {
		return testCaseRepository.countByMachinesIdNotNull();
	}
	
	public List<TestCase> retrieveTestCasesByModulesIdNotNull(Pageable pageable) {
		return testCaseRepository.findByModulesIdNotNull(pageable).getContent();
	}
	
	public Long countByModulesIdNotNull() {
		return testCaseRepository.countByModulesIdNotNull();
	}
	
	public List<TestCase> retrieveTestCasesByApplicationIdWhereModulesIdNotNull(long id, Pageable pageable) {
		return testCaseRepository.findByModulesApplicationIdAndMachinesIdNotNull(id, pageable).getContent();
	}
	
	public long countByApplicationIdWhereModulesIdNotNull(long id) {
		return testCaseRepository.countByModulesApplicationIdAndMachinesIdNotNull(id);
	}
	
	public List<TestCase> retrieveTestCasesByModuleIdWhereMachinesIdNotNull(long id, Pageable pageable) {
		return testCaseRepository.findByModulesIdAndMachinesIdNotNull(id, pageable).getContent();
	}
	
	public long countByModuleIdWhereMachinesIdNotNull(long id) {
		return testCaseRepository.countByModulesIdAndMachinesIdNotNull(id);
	}
	
	public List<TestCase> retrieveTestCasesBySpecification(Specification<TestCase> specification, Pageable pageable) {
		return testCaseRepository.findAll(specification, pageable).getContent();
	}
	
	public long countBySpecification(TestCaseSearchDTO testCaseSearchDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<TestCase> root = criteriaQuery.from(TestCase.class);
		criteriaQuery.select(criteriaBuilder.count(root));
		Predicate restrictions = TestCaseSpecification.defineSpecification(testCaseSearchDTO)
				.toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	public TestCaseDTO convertToDTO(TestCase testCase) {
		TestCaseDTO testCaseDTO = modelMapper.map(testCase, TestCaseDTO.class);
		return testCaseDTO;
	}
}
