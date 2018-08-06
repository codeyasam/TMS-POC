package com.codeyasam.testcasemanagement.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApplicationControllerIntegrationTest {
	
	@Autowired
	private ApplicationController applicationController;
	
	@Test
	public void importApplicationList() throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get("target", "tmp_uploads", "Application.csv").toAbsolutePath().toString();
		FileInputStream inputFile = new FileInputStream(path);
		MockMultipartFile multipartFile = new MockMultipartFile("file", "Application.csv", "multipart/form-data", inputFile);
		ResponseEntity<String> response = applicationController.importApplicationList(multipartFile);

		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertThat(applicationController.retrieveAll(null).getTotal(), is(equalTo(3L)));
	}
}
