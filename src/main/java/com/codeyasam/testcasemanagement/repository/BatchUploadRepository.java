package com.codeyasam.testcasemanagement.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.BatchUpload;

public interface BatchUploadRepository extends PagingAndSortingRepository<BatchUpload, Long> {

}
