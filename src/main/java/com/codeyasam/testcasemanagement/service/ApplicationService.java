package com.codeyasam.testcasemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.Application;
import com.codeyasam.testcasemanagement.dto.ApplicationDTO;
import com.codeyasam.testcasemanagement.repository.ApplicationRepository;

@Service
public class ApplicationService {
	
	private ApplicationRepository applicationRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository, ModelMapper modelMapper) {
		this.applicationRepository = applicationRepository;
		this.modelMapper = modelMapper;
	}
	
	public Application createApplication(Application application) {
		return applicationRepository.save(application);
	}
	
	public Application updateApplication(Application application) {
		return applicationRepository.save(application);
	}
	
	public Application deleteApplication(Application application) {
		application = applicationRepository.findOne(application.getId());
		applicationRepository.delete(application);
		return application;
	}
	
	public List<Application> retrieveAll(Pageable pageable) {
		return applicationRepository.findAll(pageable).getContent();
	}
	
	public long countAll() {
		return applicationRepository.count();
	}
	
	public Application searchById(long id) {
		return applicationRepository.findOne(id);
	}
	
	public Application searchByName(String name) {
		return applicationRepository.findByName(name);
	}
	
	public ApplicationDTO convertToDTO(Application application) {
		ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
		return applicationDTO;
	}
	
	public List<ApplicationDTO> convertToDTOList(List<Application> applicationList) {
		return applicationList.stream()
				.map(application -> convertToDTO(application))
				.collect(Collectors.toList());
	}
}
