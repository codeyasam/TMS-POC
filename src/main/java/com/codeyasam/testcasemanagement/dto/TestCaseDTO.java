package com.codeyasam.testcasemanagement.dto;

import java.util.Set;

import com.codeyasam.testcasemanagement.domain.Machine;
import com.codeyasam.testcasemanagement.domain.Module;
import com.codeyasam.testcasemanagement.domain.TestCaseAttribute;

public class TestCaseDTO {
	
	private long id;
	private String name;
	private Set<Machine> machines;
	private Set<Module> modules;	
	
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
	public Set<Machine> getMachines() {
		return machines;
	}
	public void setMachines(Set<Machine> machines) {
		this.machines = machines;
	}
	public Set<Module> getModules() {
		return modules;
	}
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	public TestCaseAttribute getTestCaseAttribute() {
		return testCaseAttribute;
	}
	public void setTestCaseAttribute(TestCaseAttribute testCaseAttribute) {
		this.testCaseAttribute = testCaseAttribute;
	}
	
}
