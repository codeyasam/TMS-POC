package com.codeyasam.testcasemanagement.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="module")
public class Module {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	private Application application;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="testCase")
	private Set<ModuleTestCase> moduleTestCases;
	
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
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Set<ModuleTestCase> getModuleTestCases() {
		return moduleTestCases;
	}
	public void setModuleTestCases(Set<ModuleTestCase> moduleTestCases) {
		this.moduleTestCases = moduleTestCases;
	}
	
}
