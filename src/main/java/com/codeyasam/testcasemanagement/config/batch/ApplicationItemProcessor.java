package com.codeyasam.testcasemanagement.config.batch;

import org.springframework.batch.item.ItemProcessor;

import com.codeyasam.testcasemanagement.domain.Application;

public class ApplicationItemProcessor implements ItemProcessor<Application, Application> {

	@Override
	public Application process(Application application) throws Exception {
		return application;
	}
	
}
