package com.codeyasam.testcasemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.BatchUpload;
import com.codeyasam.testcasemanagement.repository.BatchUploadRepository;

@Service
public class BatchUploadService {
	
	private BatchUploadRepository batchUploadRepository;
	
	@Autowired
	public BatchUploadService(BatchUploadRepository batchUploadRepository) {
		this.batchUploadRepository = batchUploadRepository;
	}
	
	public BatchUpload createBatchUpload(BatchUpload batchUpload) {
		return batchUploadRepository.save(batchUpload);
	}
}
