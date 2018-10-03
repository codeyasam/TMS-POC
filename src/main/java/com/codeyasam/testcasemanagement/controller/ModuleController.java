package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.MODULE_CSV_FILENAME;
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
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.codeyasam.testcasemanagement.domain.Module;
import com.codeyasam.testcasemanagement.dto.ModuleDTO;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.dto.response.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.ModuleService;
import com.codeyasam.testcasemanagement.service.TestCaseService;

@RestController
@RequestMapping("/modules")
public class ModuleController {
	
	private ModuleService moduleService;
	private TestCaseService testCaseService;
	private JobLauncher jobLauncher;
	private Job importModuleJob;
	
	@Autowired
	public ModuleController(ModuleService moduleService, 
			TestCaseService testCaseService,
			JobLauncher jobLauncher,
			@Qualifier("importModuleJob") Job importModuleJob) {
		this.moduleService = moduleService;
		this.testCaseService = testCaseService;
		this.jobLauncher = jobLauncher;
		this.importModuleJob = importModuleJob;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<ModuleDTO> createModule(@RequestBody Module module) {
		SingleDataResponse<ModuleDTO> response = new SingleDataResponse<>();
		module = moduleService.createModule(module);
		response.setData(moduleService.convertToDTO(module));
		response.setPrompt("Successfully created a module.");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<ModuleDTO> updateModule(@RequestBody Module module) {
		SingleDataResponse<ModuleDTO> response = new SingleDataResponse<>();
		module = moduleService.updateModule(module);
		response.setData(moduleService.convertToDTO(module));
		response.setPrompt("Successfully updated a module");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<ModuleDTO> deleteModule(@RequestBody Module module) {
		SingleDataResponse<ModuleDTO> response = new SingleDataResponse<>();
		module = moduleService.deleteModule(module);
		response.setData(moduleService.convertToDTO(module));
		response.setPrompt("Successfully deleted a module");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<ModuleDTO> retrieveAllModule(Pageable pageable) {
		MultipleDataResponse<ModuleDTO> response = new MultipleDataResponse<>();
		List<ModuleDTO> moduleDTOList = moduleService.retrieveAll(pageable)
				.stream()
				.map(module -> moduleService.convertToDTO(module))
				.collect(Collectors.toList());
		response.setData(moduleDTOList);
		response.setTotal(moduleService.countAll());
		response.setPrompt("Successfully retrieved modules");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public SingleDataResponse<ModuleDTO> searchById(@PathVariable long id) {
		SingleDataResponse<ModuleDTO> response = new SingleDataResponse<>();
		Module module = moduleService.searchById(id);
		response.setData(moduleService.convertToDTO(module));
		response.setPrompt("Successfully retrieved module by id.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<ModuleDTO> searchByName(@RequestParam(value="input") String name) {
		SingleDataResponse<ModuleDTO> response = new SingleDataResponse<>();
		Module module = moduleService.searchByName(name);
		response.setData(moduleService.convertToDTO(module));
		response.setPrompt("Successfully retrieved module by name.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchByApplicationId", method=RequestMethod.GET)
	public MultipleDataResponse<ModuleDTO> searchByApplicationId(@RequestParam(value="input") long id, Pageable pageable) {
		MultipleDataResponse<ModuleDTO> response = new MultipleDataResponse<>();
		List<ModuleDTO> moduleDTOList = moduleService.searchByApplicationId(id, pageable)
				.stream()
				.map(module -> moduleService.convertToDTO(module))
				.collect(Collectors.toList());
		response.setData(moduleDTOList);
		response.setTotal(moduleService.countByApplicationId(id));
		response.setPrompt("Successfully retrieved modules by application id.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/{id}/testcases/", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> retrieveTestCasesByModuleId(@PathVariable long id, Pageable pageable) {
		MultipleDataResponse<TestCaseDTO> response = new MultipleDataResponse<>();
		List<TestCaseDTO> testCaseDTOList = testCaseService.retrieveTestCasesByModuleId(id, pageable)
				.stream()
				.map(testCase -> testCaseService.convertToDTO(testCase))
				.collect(Collectors.toList());
		response.setData(testCaseDTOList);
		response.setTotal(testCaseService.countByModuleId(id));
		response.setPrompt("Successfully retrieved test cases by module id.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/import", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> importModuleList(@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("applicationId") long applicationId) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		String path = Paths.get(TARGET_FOLDER, TEMPORARY_UPLOADS_FOLDER, MODULE_CSV_FILENAME).toAbsolutePath().toString();
		File fileToImport = new File(path);
		OutputStream outputStream = new FileOutputStream(fileToImport);
		IOUtils.copy(multipartFile.getInputStream(), outputStream);
		outputStream.flush();
		outputStream.close();
		
		jobLauncher.run(importModuleJob, new JobParametersBuilder()
				.addString("fullPathFileName", fileToImport.getAbsolutePath())
				.addLong("applicationId", applicationId)
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/template/download", method=RequestMethod.GET)
	public ResponseEntity<Resource> download() throws IOException {
		Path path = Paths.get(TARGET_FOLDER, DOWNLOADABLE_TEMPLATES_FOLDER, MODULE_CSV_FILENAME);
		File file = new File(path.toString());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=modulesTemplate.csv");
	
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}
