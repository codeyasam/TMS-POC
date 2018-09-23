package com.codeyasam.testcasemanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	 @Override
	  public void addViewControllers(ViewControllerRegistry registry) {
//	      registry.addViewController("/{spring:\\w+}")
//	            .setViewName("forward:/");
//	      registry.addViewController("/**/{spring:\\w+}")
//	            .setViewName("forward:/");
//	      registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
//	            .setViewName("forward:/");
		 
		 registry.addViewController("/login**")
		 	.setViewName("forward:/");
	  }
	 
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/**")
		 	.addResourceLocations("classpath:/static/");
	 }
	
}
