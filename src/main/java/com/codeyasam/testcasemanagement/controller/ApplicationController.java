package com.codeyasam.testcasemanagement.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeyasam.testcasemanagement.domain.Application;
import com.codeyasam.testcasemanagement.dto.ApplicationDTO;
import com.codeyasam.testcasemanagement.dto.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.ApplicationService;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEMPORARY_UPLOADS_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.APPLICATION_CSV_FILENAME;

@RestController
@RequestMapping(value="/applications")
public class ApplicationController {
	
	private ApplicationService applicationService;
	private JobLauncher jobLauncher;
	private Job importApplicationJob;
	
	@Autowired
	public ApplicationController(ApplicationService applicationService, JobLauncher jobLauncher, Job importApplicationJob) {
		this.applicationService = applicationService;
		this.jobLauncher = jobLauncher;
		this.importApplicationJob = importApplicationJob;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<ApplicationDTO> createApplication(@RequestBody Application application) {
		SingleDataResponse<ApplicationDTO> response = new SingleDataResponse<>();
		application = applicationService.createApplication(application);
		response.setData(applicationService.convertToDTO(application));
		response.setPrompt("Successfully Created Application.");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<ApplicationDTO> updateApplication(@RequestBody Application application) {
		SingleDataResponse<ApplicationDTO> response = new SingleDataResponse<>();
		application = applicationService.updateApplication(application);
		response.setData(applicationService.convertToDTO(application));
		response.setPrompt("Successfully Updated Application");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<ApplicationDTO> deleteApplication(@RequestBody Application application) {
		SingleDataResponse<ApplicationDTO> response = new SingleDataResponse<>();
		application = applicationService.deleteApplication(application);
		response.setData(applicationService.convertToDTO(application));
		response.setPrompt("Successfully deleted an application");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<ApplicationDTO> retrieveAll(Pageable pageable) {
		MultipleDataResponse<ApplicationDTO> response = new MultipleDataResponse<>();
		List<ApplicationDTO> applicationDTOList = applicationService.retrieveAll(pageable)
				.stream()
				.map(application -> applicationService.convertToDTO(application))
				.collect(Collectors.toList());
		response.setData(applicationDTOList);
		response.setTotal(applicationService.countAll());
		response.setPrompt("Sucessfully retrieved applications");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/{id}/", method=RequestMethod.GET)
	public SingleDataResponse<ApplicationDTO> searchById(@PathVariable long id) {
		SingleDataResponse<ApplicationDTO> response = new SingleDataResponse<>();
		Application application = applicationService.searchById(id);
		response.setData(applicationService.convertToDTO(application));
		response.setPrompt("Successfully retrieved application by Id");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<ApplicationDTO> searchByName(@RequestParam("input") String name) {
		SingleDataResponse<ApplicationDTO> response = new SingleDataResponse<>();
		Application application = applicationService.searchByName(name);
		response.setData(applicationService.convertToDTO(application));
		response.setPrompt("Successfully retrieve application by name");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/import", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> importApplicationList(@RequestParam("file") MultipartFile multipartFile) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEMPORARY_UPLOADS_FOLDER, APPLICATION_CSV_FILENAME).toAbsolutePath().toString();
		File fileToImport = new File(path);
		OutputStream outputStream = new FileOutputStream(fileToImport);
		IOUtils.copy(multipartFile.getInputStream(), outputStream);
		outputStream.flush();
		outputStream.close();
		
		jobLauncher.run(importApplicationJob, new JobParametersBuilder()
				.addString("fullPathFileName", fileToImport.getAbsolutePath())
				.addLong("time", System.currentTimeMillis()).toJobParameters());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

