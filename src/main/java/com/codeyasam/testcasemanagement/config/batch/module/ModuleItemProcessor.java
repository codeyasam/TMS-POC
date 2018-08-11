package com.codeyasam.testcasemanagement.config.batch.module;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.dto.ModuleApplicationDTO;

public class ModuleItemProcessor implements ItemProcessor<ModuleApplicationDTO, ModuleApplicationDTO> {

	private long applicationId;
	
	public ModuleItemProcessor(Long applicationId) {
		this.applicationId = applicationId;
	}
	
	@Override
	public ModuleApplicationDTO process(ModuleApplicationDTO moduleApplicationDTO) throws Exception {
		moduleApplicationDTO.setApplicationId(applicationId);
		return moduleApplicationDTO;
	}

}
