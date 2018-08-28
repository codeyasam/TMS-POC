package com.codeyasam.testcasemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.MachineTestCase;

public interface MachineTestCaseRepository extends PagingAndSortingRepository<MachineTestCase, Long> {

	Page<MachineTestCase> findByMachineId(Long id,Pageable pageable);
	Page<MachineTestCase> findByMachineIdNotNull(Pageable pageable);	

	Long countByMachineId(long id);
	Long countByMachineIdNotNull();		
}
