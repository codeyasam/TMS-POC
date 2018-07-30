package com.codeyasam.testcasemanagement.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.domain.Machine;
import com.codeyasam.testcasemanagement.dto.MachineDTO;
import com.codeyasam.testcasemanagement.repository.MachineRepository;

@Service
public class MachineService {
	
	private MachineRepository machineRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public MachineService(MachineRepository machineRepository, ModelMapper modelMapper) {
		this.machineRepository = machineRepository;
		this.modelMapper = modelMapper;
	}
	
	public Machine createMachine(Machine machine) {
		return machineRepository.save(machine);
	}
	
	public Machine updateMachine(Machine machine) {
		return machineRepository.save(machine);
	}
	
	public List<Machine> retrieveAll(Pageable pageable) {
		return machineRepository.findAll(pageable).getContent();
	}
	
	public Machine searchByName(String name) {
		return machineRepository.findByName(name);
	}
	
	public MachineDTO convertToDTO(Machine machine) {
		MachineDTO machineDTO = modelMapper.map(machine, MachineDTO.class);
		return machineDTO;
	}
}
