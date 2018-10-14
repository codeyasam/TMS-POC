package com.codeyasam.testcasemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    private EntityManager entityManager;
	private ModelMapper modelMapper;
	
	@Autowired
	public ModuleService(ModuleRepository moduleRepository,
            EntityManager entityManager,
			ModelMapper modelMapper) {
		this.moduleRepository = moduleRepository;
        this.entityManager = entityManager;
		this.modelMapper = modelMapper;
	}
	
	public Module createModule(Module module) {
		return moduleRepository.save(module);
	}
	
	public Module updateModule(Module module) {
		return moduleRepository.save(module);
	}
	
	public Module deleteModule(Module module) {
		module = moduleRepository.findOne(module.getId());
		moduleRepository.delete(module);
		return module;
	}
	
	public List<Module> retrieveAll(Pageable pageable) {
		return moduleRepository.findAll(pageable).getContent();
	}
	
	public Long countAll() {
		return moduleRepository.count();
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
	
    public List<Module> searchByText(String searchText, Pageable pageable) {
        if (searchText.isEmpty()) searchText = "%%";
        Query query = entityManager.createQuery("select module from Module module WHERE cast(module.id as string) LIKE :searchText OR module.name LIKE :searchText")
            .setParameter("searchText", searchText)
            .setFirstResult(pageable.getOffset())
            .setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }
    
    public long countSearchByText(String searchText) {
        if (searchText.isEmpty()) searchText = "%%";
        Query query = entityManager.createQuery("select count(module.id) from Module module WHERE cast(module.id as string) LIKE :searchText OR module.name LIKE :searchText")
            .setParameter("searchText", searchText);
        return (long) query.getSingleResult();
    }
    
	public ModuleDTO convertToDTO(Module module) {
		ModuleDTO moduleDTO = modelMapper.map(module, ModuleDTO.class);
		return moduleDTO;
	}
    
    public List<ModuleDTO> convertListToDTO(List<Module> moduleList) {
        return moduleList.stream()
                .map(module -> convertToDTO(module))
                .collect(Collectors.toList());
    }
}
