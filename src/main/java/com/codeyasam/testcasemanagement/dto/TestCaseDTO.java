package com.codeyasam.testcasemanagement.dto;

import com.codeyasam.testcasemanagement.domain.TestCaseAttribute;

public class TestCaseDTO {
	
	private long id;
	private String name;
	
	private TestCaseAttribute testCaseAttribute;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public TestCaseAttribute getTestCaseAttribute() {
		return testCaseAttribute;
	}
	public void setTestCaseAttribute(TestCaseAttribute testCaseAttribute) {
		this.testCaseAttribute = testCaseAttribute;
	}
	
}
