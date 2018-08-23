package com.codeyasam.testcasemanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.TestCase;

public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long> {
	
	TestCase findByName(String name);
	Page<TestCase> findByModulesId(Long id, Pageable pageable);
	Page<TestCase> findByMachinesId(Long id,Pageable pageable);
	Page<TestCase> findByMachinesIdNotNull(Pageable pageable);
	Page<TestCase> findByModulesIdNotNull(Pageable pageable);
	Page<TestCase> findByModulesApplicationIdAndMachinesIdNotNull(Long id, Pageable pageable);
	Page<TestCase> findByModulesIdAndMachinesIdNotNull(Long id, Pageable pageable);
	Page<TestCase> findDistinctByMachinesIdIn(List<Long> idList, Pageable pageable);
	Page<TestCase> findByIdIn(List<Long> idList, Pageable pageable);
	Page<TestCase> findAll(Specification<TestCase> specification, Pageable pageable);
	
	Long countByModulesId(long id);
	Long countByMachinesId(long id);
	Long countByMachinesIdNotNull();
	Long countByModulesIdNotNull();
	Long countByModulesApplicationIdAndMachinesIdNotNull(long id);
	Long countByModulesIdAndMachinesIdNotNull(long id);
	Long countDistinctByMachinesIdIn(List<Long> idList);
	Long countByIdIn(List<Long> idList);
}
