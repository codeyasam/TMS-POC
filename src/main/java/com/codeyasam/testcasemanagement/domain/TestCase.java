package com.codeyasam.testcasemanagement.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="testcase")
public class TestCase {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToMany(mappedBy="testCases")
	private Set<Machine> machines; 
	
	@ManyToMany(mappedBy="testCases")
	private Set<Module> modules;
	
	@Embedded
	private TestCaseAttributes testCaseAttributes;
	
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
	public TestCaseAttributes getTestCaseAttributes() {
		return testCaseAttributes;
	}
	public void setTestCaseAttributes(TestCaseAttributes testCaseAttributes) {
		this.testCaseAttributes = testCaseAttributes;
	}	
}
