package com.codeyasam.testcasemanagement.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.codeyasam.testcasemanagement.domain.Application;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
	Application findByName(String name);
	
	@Modifying
	@Query("delete from Application application where application.id in ?1")
	void deleteByIds(List<Long> idList);

}
