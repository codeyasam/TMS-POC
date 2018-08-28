package com.codeyasam.testcasemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeyasam.testcasemanagement.repository.MachineTestCaseRepository;

@Service
public class MachineTestCaseService {
	
	private MachineTestCaseRepository machineTestCaseRepository;
	
	@Autowired
	public MachineTestCaseService(MachineTestCaseRepository machineTestCaseRepository) {
		this.machineTestCaseRepository = machineTestCaseRepository;
	}
	
	public long countByMachineId(long machineId) {
		return machineTestCaseRepository.countByMachineId(machineId);
	}
	
}
