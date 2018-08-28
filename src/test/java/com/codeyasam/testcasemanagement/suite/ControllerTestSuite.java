package com.codeyasam.testcasemanagement.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.testcasemanagement.controller.ApplicationControllerIntegrationTest;
import com.codeyasam.testcasemanagement.controller.MachineControllerIntegrationTest;
import com.codeyasam.testcasemanagement.controller.ModuleControllerIntegrationTest;
import com.codeyasam.testcasemanagement.controller.TestCaseControllerIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ApplicationControllerIntegrationTest.class,
	MachineControllerIntegrationTest.class,
	ModuleControllerIntegrationTest.class,
	TestCaseControllerIntegrationTest.class
})
public class ControllerTestSuite {

}
