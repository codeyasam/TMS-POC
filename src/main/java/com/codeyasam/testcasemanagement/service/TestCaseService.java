package com.codeyasam.testcasemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
		return testCaseRepository.findByModuleTestCasesModuleId(id, pageable).getContent();
	}
	
	public long countByModuleId(long id) {
		return testCaseRepository.countByModuleTestCasesModuleId(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TestCase> retrieveDistinctTestCaseByMachinesIdIn(List<Long> idList, Pageable pageable) {
		Query query = entityManager.createQuery("select distinct testCase from TestCase testCase inner join testCase.machineTestCases machineTestCase where machineTestCase.machine.id IN (?1)")
				.setParameter(1, idList)
				.setFirstResult(pageable.getOffset())
				.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}
	
	public long countDistinctByMachinesIdIn(List<Long> idList) {
		Query query = entityManager.createQuery("select count(distinct testCase.id) from TestCase testCase inner join testCase.machineTestCases machineTestCase where machineTestCase.machine.id IN (?1)")
				.setParameter(1, idList);
		return (long) query.getSingleResult();
	}
	
	public List<TestCase> retrieveTestCasesByModulesIdNotNull(Pageable pageable) {
		return testCaseRepository.findByModuleTestCasesModuleIdNotNull(pageable).getContent();
	}
	
	public Long countByModulesIdNotNull() {
		return testCaseRepository.countByModuleTestCasesModuleIdNotNull();
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
	
	public List<Long> mapTestCasesToIdList(List<TestCase> testCases) {
		return testCases
				.stream()
				.map(testcase -> testcase.getId())
				.collect(Collectors.toList());
	}	
	
	@SuppressWarnings("unchecked")
	public List<TestCase> retrieveTestCasesByMachineId(long id, Pageable pageable) {
		Query query = entityManager.createQuery("select testCase from TestCase testCase inner join testCase.machineTestCases machineTestCase where machineTestCase.machine.id = ?1")
				.setParameter(1, id)
				.setFirstResult(pageable.getOffset())
				.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}	
	
	public long countByMacineId(long id) {
		Query query = entityManager.createQuery("select count(*) from TestCase testCase inner join testCase.machineTestCases machineTestCase where machineTestCase.machine.id = ?1")
				.setParameter(1, id);
		return (long) query.getSingleResult();
	}
	
	public TestCaseDTO convertToDTO(TestCase testCase) {
		TestCaseDTO testCaseDTO = modelMapper.map(testCase, TestCaseDTO.class);
		return testCaseDTO;
	}

}
