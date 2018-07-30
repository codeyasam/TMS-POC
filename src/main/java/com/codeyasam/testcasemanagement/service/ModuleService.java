package com.codeyasam.testcasemanagement.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.Module;
import com.codeyasam.testcasemanagement.dto.ModuleDTO;
import com.codeyasam.testcasemanagement.repository.ModuleRepository;

@Service
public class ModuleService {
	
	private ModuleRepository moduleRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public ModuleService(ModuleRepository moduleRepository, ModelMapper modelMapper) {
		this.moduleRepository = moduleRepository;
		this.modelMapper = modelMapper;
	}
	
	public Module createModule(Module module) {
		return moduleRepository.save(module);
	}
	
	public Module updateModule(Module module) {
		return moduleRepository.save(module);
	}
	
	public List<Module> retrieveAll(Pageable pageable) {
		return moduleRepository.findAll(pageable).getContent();
	}
	
	public List<Module> searchByApplicationId(Long id, Pageable pageable) {
		return moduleRepository.findByApplicationId(id, pageable).getContent();
	}
	
	public Long countByApplicationId(Long id) {
		return moduleRepository.countByApplicationId(id);
	}
	
	public Module searchById(Long id) {
		return moduleRepository.findById(id);
	}
	
	public Module searchByName(String name) {
		return moduleRepository.findByName(name);
	}
	
	public ModuleDTO convertToDTO(Module module) {
		ModuleDTO moduleDTO = modelMapper.map(module, ModuleDTO.class);
		return moduleDTO;
	}
}
