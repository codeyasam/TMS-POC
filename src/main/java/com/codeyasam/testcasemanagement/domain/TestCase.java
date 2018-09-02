package com.codeyasam.testcasemanagement.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@OneToMany(mappedBy="testCase", fetch=FetchType.LAZY)
	private Set<ModuleTestCase> moduleTestCases;
	
	@OneToMany(mappedBy="testCase", fetch=FetchType.LAZY)
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
	public Set<ModuleTestCase> getModuleTestCases() {
		return moduleTestCases;
	}
	public void setModuleTestCases(Set<ModuleTestCase> moduleTestCases) {
		this.moduleTestCases = moduleTestCases;
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
