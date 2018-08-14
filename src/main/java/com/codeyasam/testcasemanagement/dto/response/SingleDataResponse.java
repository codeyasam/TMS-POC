package com.codeyasam.testcasemanagement.dto.response;

public class SingleDataResponse<T> extends DataResponse {
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

	public static class Builder<T> {
		
		private T data;
		private String prompt;
		private int status;
				
		public Builder<T> setData(T data) {
			this.data = data;
			return this;
		}
		public Builder<T> setPrompt(String prompt) {
			this.prompt = prompt;
			return this;
		}
		public Builder<T> setStatus(int status) {
			this.status = status;
			return this;
		}
		
		public SingleDataResponse<T> build() {
			SingleDataResponse<T> response = new SingleDataResponse<>();
			response.setData(data);
			response.setPrompt(prompt);
			response.setStatus(status);
			return response;
		}
	}
}
