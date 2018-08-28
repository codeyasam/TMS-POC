package com.codeyasam.testcasemanagement.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="testcase")
public class TestCase extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToMany(mappedBy="testCases")
	private Set<Module> modules;
	
	@OneToMany(mappedBy="testCase")
	private Set<MachineTestCase> machineTestCases;
	
	@Embedded
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
	public Set<Module> getModules() {
		return modules;
	}
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	public TestCaseAttribute getTestCaseAttribute() {
		return testCaseAttribute;
	}
	public void setTestCaseAttribute(TestCaseAttribute testCaseAttributes) {
		this.testCaseAttribute = testCaseAttributes;
	}
	public Set<MachineTestCase> getMachineTestCases() {
		return machineTestCases;
	}
	public void setMachineTestCases(Set<MachineTestCase> machineTestCases) {
		this.machineTestCases = machineTestCases;
	}
	
}
