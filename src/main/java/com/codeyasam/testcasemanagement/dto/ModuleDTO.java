package com.codeyasam.testcasemanagement.dto;

public class ModuleDTO {
	
	private long id;
	private String name;
	private ApplicationDTO application;
	
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
	public ApplicationDTO getApplication() {
		return application;
	}
	public void setApplication(ApplicationDTO application) {
		this.application = application;
	}	
}
