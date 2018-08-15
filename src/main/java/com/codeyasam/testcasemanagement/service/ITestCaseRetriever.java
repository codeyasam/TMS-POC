package com.codeyasam.testcasemanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.codeyasam.testcasemanagement.domain.TestCase;

@FunctionalInterface
public interface ITestCaseRetriever {
	
	List<TestCase> retrieve(long id, Pageable pageable);
	
}
