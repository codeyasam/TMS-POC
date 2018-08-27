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

import com.codeyasam.testcasemanagement.domain.BatchUpload;
import com.codeyasam.testcasemanagement.domain.specification.TestCaseSpecification.SearchType;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.exception.TestCaseSearchException;
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
		long moduleId = 1;
		String testCaseFileName = TEST_TESTCASE_CSV_FILENAME;
		SingleDataResponse<BatchUpload> response = importTestCasesToModuleFromFlatFile(moduleId, testCaseFileName);
	
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(2, testCaseService.countByModuleId(1));
	}
	
	@Test
	public void importTestCasesToMachineByModule() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		long machineId = 1;
		long moduleId = 2;
		final String testCaseToMachineByModuleCsvFileName = "TestCasesToMachineByModule.csv";
		importTestCasesToModuleFromFlatFile(moduleId, testCaseToMachineByModuleCsvFileName);
		
		ResponseEntity<HttpStatus> response = testCaseController.importTestCasesToMachineByModule(machineId, moduleId);
		
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		Assert.assertEquals(2, testCaseService.countByMachineId(machineId));
	}
	
	@Test
	public void importTestCasesByFilter() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException, TestCaseSearchException {
		long moduleId = 3;
		String testCaseFileName = "TestCasesForModuleByFilter.csv";
		importTestCasesToModuleFromFlatFile(moduleId, testCaseFileName);
		TestCaseSearchDTO testCaseSearchDTO = new TestCaseSearchDTO();
		testCaseSearchDTO.setText("");
		testCaseSearchDTO.setType(SearchType.ALL.type());
		testCaseSearchDTO.setModuleId(moduleId);
		testCaseSearchDTO.setPriority(1);
		testCaseSearchDTO.setIsPriority(true);
		testCaseSearchDTO.setIsMandatory(0);
		testCaseSearchDTO.setIsSmoke(0);
		ResponseEntity<HttpStatus> response = testCaseController.importTestCasesToMachineByFilter(testCaseSearchDTO.getText(), 
				testCaseSearchDTO.getType(), moduleId, testCaseSearchDTO.getPriority(), testCaseSearchDTO.getIsPriority(), 
				testCaseSearchDTO.getIsMandatory(), testCaseSearchDTO.getIsSmoke());
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(2, testCaseService.countBySpecification(testCaseSearchDTO));
	}
	
	private SingleDataResponse<BatchUpload> importTestCasesToModuleFromFlatFile(long moduleId, String testCaseFileName) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEST_UPLOADS_FOLDER, testCaseFileName).toAbsolutePath().toString();
		FileInputStream inputFile = new FileInputStream(path);
		MockMultipartFile multipartFile = new MockMultipartFile("file", testCaseFileName, "multipart/form-data", inputFile);
		return testCaseController.importTestCase(multipartFile, moduleId);		
	}
}
