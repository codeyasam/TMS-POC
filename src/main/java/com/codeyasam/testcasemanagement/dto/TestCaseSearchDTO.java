package com.codeyasam.testcasemanagement.dto;

public class TestCaseSearchDTO {
	
	private String text;
	private String type;
	
	private Long batchId; 
	private Long moduleId;
	private String description;
	private String location;
	private long priority;
	private long isSmoke;
	private long isMandatory;
	private long isPriority;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
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
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public long getIsSmoke() {
		return isSmoke;
	}
	public void setIsSmoke(long isSmoke) {
		this.isSmoke = isSmoke;
	}
	public long getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(long isMandatory) {
		this.isMandatory = isMandatory;
	}
	public long getIsPriority() {
		return isPriority;
	}
	public void setIsPriority(long isPriority) {
		this.isPriority = isPriority;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
}
