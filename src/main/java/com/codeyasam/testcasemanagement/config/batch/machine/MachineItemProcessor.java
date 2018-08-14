package com.codeyasam.testcasemanagement.config.batch.machine;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.dto.MachineDTO;

public class MachineItemProcessor implements ItemProcessor<MachineDTO, MachineDTO> {

	@Override
	public MachineDTO process(MachineDTO machineDTO) throws Exception {
		return machineDTO;
	}

}
