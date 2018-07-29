package com.codeyasam.testcasemanagement.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.Application;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
	Application findByName(String name);
}
