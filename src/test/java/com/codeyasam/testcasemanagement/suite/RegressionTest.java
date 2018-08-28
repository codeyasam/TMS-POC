package com.codeyasam.testcasemanagement.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ControllerTestSuite.class,
	ServiceTestSuite.class
})
public class RegressionTest {

}
