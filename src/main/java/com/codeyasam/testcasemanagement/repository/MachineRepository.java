package com.codeyasam.testcasemanagement.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.Machine;

public interface MachineRepository extends PagingAndSortingRepository<Machine, Long> {
	Machine findByName(String name);
}
