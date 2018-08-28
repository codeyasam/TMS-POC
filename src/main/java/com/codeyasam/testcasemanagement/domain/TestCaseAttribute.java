package com.codeyasam.testcasemanagement.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class TestCaseAttribute {
	
	@ManyToOne
	private BatchUpload batchUpload;
	private String description;
	private String location;
	private Long priority;
	private Boolean isSmoke;
	private Boolean isMandatory;
	
	public BatchUpload getBatchUpload() {
		return batchUpload;
	}
	
	public void setBatchUpload(BatchUpload batchUpload) {
		this.batchUpload = batchUpload;
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
	public Long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
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
