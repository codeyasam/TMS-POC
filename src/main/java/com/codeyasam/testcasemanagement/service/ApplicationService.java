package com.codeyasam.testcasemanagement.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.testcasemanagement.domain.Application;
import com.codeyasam.testcasemanagement.dto.ApplicationDTO;
import com.codeyasam.testcasemanagement.repository.ApplicationRepository;

@Service
public class ApplicationService {
	
	private ApplicationRepository applicationRepository;
	private EntityManager entityManager;
    private ModelMapper modelMapper;
	
	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository, EntityManager entityManager, ModelMapper modelMapper) {
		this.applicationRepository = applicationRepository;
        this.entityManager = entityManager;
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
    
    public List<Application> retrieveBySearchText(String searchText, Pageable pageable) {
        if (searchText.isEmpty()) searchText="%%";
        Query query = entityManager.createQuery("select application from Application application WHERE cast(application.id as string) LIKE :searchText OR application.name LIKE :searchText")
            .setParameter("searchText", searchText)
            .setFirstResult(pageable.getOffset())
            .setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }
    
    public long countBySearchText(String searchText) {
        if (searchText.isEmpty()) searchText="%%";
        Query query = entityManager.createQuery("select count(application.id) from Application application WHERE cast(application.id as string) LIKE :searchText OR application.name LIKE :searchText")
            .setParameter("searchText", searchText);
        return (long) query.getSingleResult();
    }
	
	@Transactional("transactionManager")
	public void deleteApplications(List<Application> applications) {
		List<Long> idList = new ArrayList<>();
		applications.forEach(application -> idList.add(application.getId()));
		applicationRepository.deleteByIds(idList);
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
