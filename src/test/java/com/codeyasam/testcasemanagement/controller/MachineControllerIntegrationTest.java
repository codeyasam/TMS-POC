package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEST_MACHINE_CSV_FILENAME;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEST_UPLOADS_FOLDER;

import static org.junit.Assert.assertEquals;

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
public class MachineControllerIntegrationTest {
	
	@Autowired
	private MachineController machineController;
	
	@Test
	public void importMachineList() throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEST_UPLOADS_FOLDER, TEST_MACHINE_CSV_FILENAME).toAbsolutePath().toString();
		FileInputStream inputFile = new FileInputStream(path);
		MockMultipartFile multipartFile = new MockMultipartFile("file", TEST_MACHINE_CSV_FILENAME, "multipart/form-data", inputFile);
		ResponseEntity<HttpStatus> response = machineController.importMachineList(multipartFile);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(6, machineController.retrieveAll(null).getTotal());
	}
}
