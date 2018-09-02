package com.codeyasam.testcasemanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.TestCase;

public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long> {
	
	TestCase findByName(String name);
	Page<TestCase> findByModuleTestCasesModuleId(Long id, Pageable pageable);
	Page<TestCase> findByModuleTestCasesModuleIdNotNull(Pageable pageable);
	Page<TestCase> findByIdIn(List<Long> idList, Pageable pageable);
	Page<TestCase> findAll(Specification<TestCase> specification, Pageable pageable);
	
	Long countByModuleTestCasesModuleId(long id);
	Long countByModuleTestCasesModuleIdNotNull(); 
	Long countByIdIn(List<Long> idList);
}
