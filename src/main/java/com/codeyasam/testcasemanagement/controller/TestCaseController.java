package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEMPORARY_UPLOADS_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TESTCASE_CSV_FILENAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.h2.util.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeyasam.testcasemanagement.domain.BatchUpload;
import com.codeyasam.testcasemanagement.domain.BatchUpload.BatchUploadType;
import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.domain.specification.TestCaseSpecification;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;
import com.codeyasam.testcasemanagement.dto.response.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.BatchUploadService;
import com.codeyasam.testcasemanagement.service.TestCaseService;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {
	
	private TestCaseService testCaseService;
	private BatchUploadService batchUploadService;
	private JobLauncher jobLauncher;
	private Job importTestCaseJob;
	
	@Autowired
	public TestCaseController(TestCaseService testCaseService,
			BatchUploadService batchUploadService,
			JobLauncher jobLauncher,
			@Qualifier("importTestCaseJob") Job importTestCaseJob) {
		this.testCaseService = testCaseService;
		this.batchUploadService = batchUploadService;
		this.jobLauncher = jobLauncher;
		this.importTestCaseJob = importTestCaseJob;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<TestCaseDTO> createTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.createTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully created test case.")
				.setStatus(HttpStatus.CREATED.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<TestCaseDTO> updateTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.updateTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully updated test case.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<TestCaseDTO> deleteTestCase(@RequestBody TestCase testCase) {
		testCase = testCaseService.deleteTestCase(testCase);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully deleted test case.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> retrieveAll(Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveAll(pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countAll())
				.setPrompt("Successfully retrieved all test cases.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/{id}/", method=RequestMethod.GET)
	public SingleDataResponse<TestCaseDTO> searchById(@PathVariable long id) {
		TestCase testCase = testCaseService.searchById(id);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully retrieved test case by id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<TestCaseDTO> searchByName(@RequestParam("input") String name) {
		TestCase testCase = testCaseService.searchByName(name);
		return new SingleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseService.convertToDTO(testCase))
				.setPrompt("Successfully retrieved test case by name.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByModuleId", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByModuleId(@RequestParam("input") long moduleId, Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByModuleId(moduleId, pageable);	
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByModuleId(moduleId))
				.setPrompt("Successfully retrieved test cases by module id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="searchByMachineId", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByMachineId(@RequestParam("input") long machineId, Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByMachineId(machineId, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByMachineId(machineId))
				.setPrompt("Successfully retrieved test cases by machine id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByMachineIdNotNull", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByMahineIdNotNull(Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByMachinesIdNotNull(pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByMachinesIdNotNull())
				.setPrompt("Successfully retrieved test cases where machines id not null")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByModuleIdNotNull", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByModulesIdNotNull(Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByModulesIdNotNull(pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByModulesIdNotNull())
				.setPrompt("Sucessfully retrieved test cases where modules id not null")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByApplicationIdWhereModuleIdNotNull", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByApplicationIdWhereModuleIdNotNull(@RequestParam("input") long id, Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByApplicationIdWhereModulesIdNotNull(id, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByApplicationIdWhereModulesIdNotNull(id))
				.setPrompt("Successfully retrieved test cases by application id where modules id not null")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByModuleIdWhereMachinesIdNotNull", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> searchByModuleIdWhereMachinesIdNotNull(long id, Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesByModuleIdWhereMachinesIdNotNull(id, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByModuleIdWhereMachinesIdNotNull(id))
				.setPrompt("Successfully retrieved test cases by module id where machines not null")
				.build();
	}
	
	@RequestMapping(value="/retrieveDistinctTestCasesByMachinesIdIn")
	public MultipleDataResponse<TestCaseDTO> retrieveDistinctTestCasesByMachinesId(@RequestParam("input") List<Long> idList, Pageable pageable) {
		List<TestCase> testCaseList = testCaseService.retrieveDistinctTestCaseByMachinesIdIn(idList, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countDistinctByMachinesIdIn(idList))
				.setPrompt("Successfully retrieved distinct test cases by machine ids")
				.build();
	}
	
	@RequestMapping(value="/retrieveBySpecification", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> retrieveBySpecification(@RequestParam("text") String text,
			@RequestParam("moduleId") long moduleId,
			@RequestParam("filterType") String filterType,
			@RequestParam("isSmoke") boolean isSmoke,
			@RequestParam("isMandatory") boolean isMandatory,
			@RequestParam("isPriority") boolean isPriority,
			@RequestParam("priority") int priority
		,Pageable pageable) {
		
		TestCaseSearchDTO testCaseSearchDTO = new TestCaseSearchDTO();
		testCaseSearchDTO.setText(text);
		testCaseSearchDTO.setModuleId(moduleId);
		testCaseSearchDTO.setIsSmoke(isSmoke);
		testCaseSearchDTO.setIsPriority(isPriority);
		testCaseSearchDTO.setPriority(priority);
		
		Specification<TestCase> specification = TestCaseSpecification.defineSpecification(testCaseSearchDTO);
		List<TestCase> testCaseList = testCaseService.retrieveTestCasesBySpecification(specification, pageable);
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(testCaseList);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countBySpecification(testCaseSearchDTO))
				.setPrompt("Successfully retrieve test cases by specification")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/importTestCases", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> importTestCase(@RequestParam("file") MultipartFile multipartFile, @RequestParam("moduleId") long moduleId) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEMPORARY_UPLOADS_FOLDER, TESTCASE_CSV_FILENAME).toAbsolutePath().toString();
		File fileToImport = new File(path);
		OutputStream outputStream = new FileOutputStream(fileToImport);
		IOUtils.copy(multipartFile.getInputStream(), outputStream);
		outputStream.flush();
		outputStream.close();
		
		BatchUploadType batchUploadType = BatchUploadType.TESTCASE;
		long batchId = batchUploadService.createBatchUpload(new BatchUpload(batchUploadType)).getId();
		jobLauncher.run(importTestCaseJob, new JobParametersBuilder()
				.addString("fullPathFileName", fileToImport.getAbsolutePath())
				.addLong("moduleId", moduleId)
				.addLong("batchId", batchId)
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
		
	private List<TestCaseDTO> mapTestCaseListToDTO(List<TestCase> testCaseList) {
		return testCaseList.stream()
				.map(testcase -> testCaseService.convertToDTO(testcase))
				.collect(Collectors.toList());
	}
}

