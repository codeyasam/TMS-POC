package com.codeyasam.testcasemanagement.dto.response;

import java.util.List;

public class MultipleDataResponse<T> extends DataResponse {
	
	private List<T> data;
	private long total;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public static class Builder<T> {
		private List<T> data;
		private long total;
		private String prompt;
		private int status;
		
		public Builder<T> setData(List<T> data) {
			this.data = data;
			return this;
		}
		public Builder<T> setTotal(long total) {
			this.total = total;
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
		public MultipleDataResponse<T> build() {
			MultipleDataResponse<T> response = new MultipleDataResponse<>();
			response.setData(data);
			response.setTotal(total);
			response.setPrompt(prompt);
			response.setStatus(status);
			return response;
		}
	}
}
