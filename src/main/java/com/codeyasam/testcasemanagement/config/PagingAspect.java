package com.codeyasam.testcasemanagement.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//@Aspect
//@Component
public class PagingAspect {
	
	@Pointcut("within(com.citco.qa.system.restcontroller.*) && (args(pageable,..) || args(..,pageable))")
	public void restControllerGetMethods(Pageable pageable) {}
	
	@Around("restControllerGetMethods(pageable)")
	public Object Pageable(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Pageable) {
				pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());
				args[i] = pageable;
			}
		}
		return pjp.proceed(args);
	}
}
