package com.codeyasam.testcasemanagement.domain;

import javax.persistence.Embeddable;

@Embeddable
public class TestCaseAttributes {
	
	private Long batchId;
	private String description;
	private String location;
	private int priority;
	private boolean isSmoke;
	private boolean isMandatory;
	
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
	public boolean getIsSmoke() {
		return isSmoke;
	}
	public void setIsSmoke(boolean isSmoke) {
		this.isSmoke = isSmoke;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
}
