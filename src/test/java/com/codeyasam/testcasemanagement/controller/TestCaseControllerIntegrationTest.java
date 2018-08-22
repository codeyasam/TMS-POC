package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEST_TESTCASE_CSV_FILENAME;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEST_UPLOADS_FOLDER;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Assert;
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

import com.codeyasam.testcasemanagement.service.TestCaseService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestCaseControllerIntegrationTest {
	
	@Autowired
	private TestCaseController testCaseController;
	
	@Autowired
	private TestCaseService testCaseService;

	@Test
	public void importTestCaseList() throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEST_UPLOADS_FOLDER, TEST_TESTCASE_CSV_FILENAME).toAbsolutePath().toString();
		FileInputStream inputFile = new FileInputStream(path);
		MockMultipartFile multipartFile = new MockMultipartFile("file", TEST_TESTCASE_CSV_FILENAME, "multipart/form-data", inputFile);
		ResponseEntity<HttpStatus> response = testCaseController.importTestCase(multipartFile, 1);
	
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(3, testCaseController.retrieveAll(null).getTotal());
		Assert.assertEquals(0, testCaseService.countByModuleId(2));
	}
}
