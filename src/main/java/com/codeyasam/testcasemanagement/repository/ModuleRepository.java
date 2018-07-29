package com.codeyasam.testcasemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.Module;

public interface ModuleRepository extends PagingAndSortingRepository<Module, Long> {
	
	Page<Module> findByApplicationId(Long id, Pageable pageable);
	Module findById(long id);
	Module findByName(String name);
	
	Long countByApplicationId(long id);
	
}
