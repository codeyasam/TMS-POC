package com.codeyasam.testcasemanagement.domain;

import javax.persistence.Embeddable;

@Embeddable
public class TestCaseAttribute {
	
	private Long batchId;
	private String description;
	private String location;
	private int priority;
	private Boolean isSmoke;
	private Boolean isMandatory;
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Boolean getIsSmoke() {
		return isSmoke;
	}
	public void setIsSmoke(Boolean isSmoke) {
		this.isSmoke = isSmoke;
	}
	public Boolean getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}	
}
