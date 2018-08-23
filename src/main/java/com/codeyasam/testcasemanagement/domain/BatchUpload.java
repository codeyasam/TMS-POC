package com.codeyasam.testcasemanagement.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="batch_upload")
public class BatchUpload {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private BatchUploadType batchUploadType;
	
	public BatchUpload() {
		
	}
	
	public BatchUpload(long id) {
		this.id = id;
	}
	
	public BatchUpload(BatchUploadType batchUploadType) {
		this.batchUploadType = batchUploadType;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BatchUploadType getBatchUploadType() {
		return batchUploadType;
	}

	public void setBatchUploadType(BatchUploadType batchUploadType) {
		this.batchUploadType = batchUploadType;
	}

	public static enum BatchUploadType {
		APPLICATION,
		MACHINE,
		MODULE,
		TESTCASE
	}
}
