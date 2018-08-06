package com.codeyasam.testcasemanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
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
	Page<TestCase> findByTestCaseAttributeBatchIdNotNullOrderByTestCaseAttributeBatchId(Pageable pageable);
	Page<TestCase> findByIdIn(List<Long> idList, Pageable pageable);
	Page<TestCase> findAll(Specification<TestCase> specification, Pageable pageable);
	
	Long countByModulesId(long id);
	Long countByMachinesId(long id);
	Long countByMachinesIdNotNull(long id);
	Long countByModulesIdNotNull(long id);
	Long countByModulesApplicationIdAndMachinesIdNotNull(long id);
	Long countByModulesIdAndMachinesIdNotNull(long id);
	Long countDistinctByMachinesIdIn(List<Long> idList);
	Long countByTestCaseAttributeBatchIdNotNull();
	Long countByIdIn(List<Long> idList);
	
	@Query("SELECT count(t) FROM TestCase t GROUP BY t.testCaseAttribute.batchId")
	Long queryDistinctBatchIdCount();
}
