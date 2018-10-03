package com.codeyasam.testcasemanagement.controller;

import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.MACHINE_CSV_FILENAME;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TARGET_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.TEMPORARY_UPLOADS_FOLDER;
import static com.codeyasam.testcasemanagement.config.TestCaseConfigConstant.DOWNLOADABLE_TEMPLATES_FOLDER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
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

import com.codeyasam.testcasemanagement.domain.Machine;
import com.codeyasam.testcasemanagement.dto.MachineDTO;
import com.codeyasam.testcasemanagement.dto.TestCaseDTO;
import com.codeyasam.testcasemanagement.dto.response.MultipleDataResponse;
import com.codeyasam.testcasemanagement.dto.response.SingleDataResponse;
import com.codeyasam.testcasemanagement.service.MachineService;
import com.codeyasam.testcasemanagement.service.TestCaseService;

@RestController
@RequestMapping("/machines")
public class MachineController {
	
	private MachineService machineService;
	private TestCaseService testCaseService;
	private JobLauncher jobLauncher;
	private Job importMachineJob;
	
	@Autowired
	public MachineController(MachineService machineService, 
			TestCaseService testCaseService,
			JobLauncher jobLauncher,
			@Qualifier("importMachineJob") Job importMachineJob) {
		this.machineService = machineService;
		this.testCaseService = testCaseService;
		this.jobLauncher = jobLauncher;
		this.importMachineJob = importMachineJob;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<MachineDTO> createMachine(@RequestBody Machine machine) {
		machine = machineService.createMachine(machine);
		return new SingleDataResponse.Builder<MachineDTO>()
				.setData(machineService.convertToDTO(machine))
				.setPrompt("Successfully created machine.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<MachineDTO> updateMachine(@RequestBody Machine machine) {
		machine = machineService.updateMachine(machine);
		return new SingleDataResponse.Builder<MachineDTO>()
				.setData(machineService.convertToDTO(machine))
				.setPrompt("Successfully updated machine.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public SingleDataResponse<MachineDTO> deleteMachine(@RequestBody Machine machine) {
		machine = machineService.deleteMachine(machine);
		return new SingleDataResponse.Builder<MachineDTO>()
				.setData(machineService.convertToDTO(machine))
				.setPrompt("Successfully deleted machine.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<MachineDTO> retrieveAll(Pageable pageable) {
		List<MachineDTO> machineDTOList = mapMachineListToDTO(machineService.retrieveAll(pageable));		
		return new MultipleDataResponse.Builder<MachineDTO>()
				.setData(machineDTOList)
				.setTotal(machineService.countAll())
				.setPrompt("Successfully retrieved machines.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	private List<MachineDTO> mapMachineListToDTO(List<Machine> machineList) {
		return machineList.stream()
				.map(machine -> machineService.convertToDTO(machine))
				.collect(Collectors.toList());
	}
	
	@RequestMapping(value="/{id}/", method=RequestMethod.GET)
	public SingleDataResponse<MachineDTO> searchById(@PathVariable long id) {
		Machine machine = machineService.searchById(id);
		return new SingleDataResponse.Builder<MachineDTO>()
				.setData(machineService.convertToDTO(machine))
				.setPrompt("Successfully retrieved machine by ID.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/searchByName", method=RequestMethod.GET)
	public SingleDataResponse<MachineDTO> searchByName(@RequestParam("input") String name) {
		Machine machine = machineService.searchByName(name);
		return new SingleDataResponse.Builder<MachineDTO>()
				.setData(machineService.convertToDTO(machine))
				.setPrompt("Successfully retrieved machine by name.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	@RequestMapping(value="/{id}/testcases/", method=RequestMethod.GET)
	public MultipleDataResponse<TestCaseDTO> retrieveTestCasesByMachineId(@PathVariable long id, Pageable pageable) {
		List<TestCaseDTO> testCaseDTOList = mapTestCaseListToDTO(id, pageable);
		return new MultipleDataResponse.Builder<TestCaseDTO>()
				.setData(testCaseDTOList)
				.setTotal(testCaseService.countByMacineId(id))
				.setPrompt("Successfully retrieved test cases by machine id.")
				.setStatus(HttpStatus.OK.value())
				.build();
	}
	
	private List<TestCaseDTO> mapTestCaseListToDTO(long id, Pageable pageable) {
		return testCaseService.retrieveTestCasesByMachineId(id, pageable)
				.stream()
				.map(testcase -> testCaseService.convertToDTO(testcase))
				.collect(Collectors.toList());		
	}
	
	@RequestMapping(value="/import", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> importMachineList(@RequestParam("file") MultipartFile multipartFile) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		String path = Paths.get(TARGET_FOLDER, TEMPORARY_UPLOADS_FOLDER, MACHINE_CSV_FILENAME).toAbsolutePath().toString();
		File fileToImport = new File(path);
		FileOutputStream outputStream = new FileOutputStream(fileToImport);
		IOUtils.copy(multipartFile.getInputStream(), outputStream);
		outputStream.flush();
		outputStream.close();
		
		jobLauncher.run(importMachineJob, new JobParametersBuilder()
				.addString("fullPathFileName", fileToImport.getAbsolutePath())
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/template/download", method=RequestMethod.GET)
	public ResponseEntity<Resource> download() throws IOException {
		Path path = Paths.get(TARGET_FOLDER, DOWNLOADABLE_TEMPLATES_FOLDER, MACHINE_CSV_FILENAME).toAbsolutePath();
		File file = new File(path.toString());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=machinesTemplate.csv");
		
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
	
}
