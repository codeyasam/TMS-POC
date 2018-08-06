package com.codeyasam.testcasemanagement.dto;

public abstract class DataResponse {
	
	private String prompt;
	private int status;
	
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
