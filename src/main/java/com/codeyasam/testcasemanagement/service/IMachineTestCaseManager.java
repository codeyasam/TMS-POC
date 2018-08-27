package com.codeyasam.testcasemanagement.service;

import java.util.List;

import com.codeyasam.testcasemanagement.domain.Machine;
import com.codeyasam.testcasemanagement.domain.TestCase;

@FunctionalInterface
public interface IMachineTestCaseManager {
	void modify(Machine machine, List<TestCase> testCaseList);
}
