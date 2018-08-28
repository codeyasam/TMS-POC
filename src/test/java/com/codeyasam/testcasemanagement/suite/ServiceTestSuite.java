package com.codeyasam.testcasemanagement.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.testcasemanagement.service.TestCaseServiceIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestCaseServiceIntegrationTest.class
})
public class ServiceTestSuite {

}
