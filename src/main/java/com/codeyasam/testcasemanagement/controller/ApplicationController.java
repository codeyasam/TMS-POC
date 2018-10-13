package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.APPLICATION_CSV_FILENAME;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEMPORARY_UPLOADS_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.DOWNLOADABLE_TEMPLATES_FOLDER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.codeyasam.testcasemanagement.domain.Application;
import com.codeyasam.testcasemanagement.dto.ApplicationDTO;
import com.codeyasam.testcasemanagement.dto.response.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.ApplicationService;

@RestController
@RequestMapping(value="/api/applications")
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
		application = applicationService.createApplication(application);
		return new SingleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationService.convertToDTO(application))
				.setPrompt("Successfully Created Application.")
				.setStatus(HttpStatus.CREATED.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<ApplicationDTO> updateApplication(@RequestBody Application application) {
		application = applicationService.updateApplication(application);
		return new SingleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationService.convertToDTO(application))
				.setPrompt("Successfully Updated Application")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<ApplicationDTO> deleteApplication(@RequestBody Application application) {
		application = applicationService.deleteApplication(application);
		return new SingleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationService.convertToDTO(application))
				.setPrompt("Successfully deleted an application")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<ApplicationDTO> retrieveAll(Pageable pageable) {
		List<Application> applicationList = applicationService.retrieveAll(pageable);
		List<ApplicationDTO> applicationDTOList = applicationService.convertToDTOList(applicationList);
		return new MultipleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationDTOList)
				.setTotal(applicationService.countAll())
				.setPrompt("Successfully retrieved applications.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/{id}/", method=RequestMethod.GET)
	public SingleDataResponse<ApplicationDTO> searchById(@PathVariable long id) {
		Application application = applicationService.searchById(id);
		return new SingleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationService.convertToDTO(application))
				.setPrompt("Successfully retrieved application by Id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<ApplicationDTO> searchByName(@RequestParam("input") String name) {
		Application application = applicationService.searchByName(name);
		return new SingleDataResponse.Builder<ApplicationDTO>()
				.setData(applicationService.convertToDTO(application))
				.setPrompt("Successfully retrieved application by name.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
    
    @RequestMapping(value="/searchByText", method=RequestMethod.GET)
    public MultipleDataResponse<ApplicationDTO> retrieveBySearchText(@RequestParam("input") String searchText, Pageable pageable) {
        List<Application> applicationList = applicationService.retrieveBySearchText(searchText, pageable);
        List<ApplicationDTO> applicationDTOList = applicationService.convertToDTOList(applicationList);
        return new MultipleDataResponse.Builder<ApplicationDTO>()
            .setData(applicationDTOList)
            .setTotal(applicationService.countBySearchText(searchText))
            .setPrompt("Successfully retrieved applications.")
            .setStatus(HttpStatus.OK.value())
            .build();
    }
	
	@RequestMapping(value="/deleteApplications", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteApplications(@RequestBody List<Application> applications) {
		applicationService.deleteApplications(applications);
		return new ResponseEntity<>(HttpStatus.OK);
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
	
	@RequestMapping(value="/template/download", method=RequestMethod.GET)
	public ResponseEntity<Resource> download() throws IOException {
		Path path = Paths.get(TARGET_FOLDER, DOWNLOADABLE_TEMPLATES_FOLDER, APPLICATION_CSV_FILENAME).toAbsolutePath();
		File file = new File(path.toString());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=applicationsTemplate.csv");
		
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
	
}

