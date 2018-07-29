package com.codeyasam.testcasemanagement.domain.specification;

import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.testcasemanagement.domain.Module;
import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;

public class TestCaseSpecification {
	
	private static final String ALL = "all";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String PRIORITY_ONLY = "priorityOnly";
	private static final String SMOKE_ONLY = "smokeOnly";
	private static final String MANDATORY_ONLY = "mandatoryOnly";
	
	public static Specification<TestCase> defineSpecification(TestCaseSearchDTO testCaseSearchDTO) {
		String text = testCaseSearchDTO.getText().trim();
		if (text.isEmpty()) text = "%" + text + "%";
		testCaseSearchDTO.setText(text);
		
		return (root, query, builder) -> {
			final String searchText = testCaseSearchDTO.getText().toLowerCase().trim();
			Predicate id          = builder.like(builder.concat("", root.get("id")), searchText);
			Predicate name        = builder.like(builder.lower(root.get("name")), searchText);
			Predicate description = builder.like(builder.lower(root.get("description")), searchText);
			Predicate location    = builder.like(builder.lower(root.get("location")), searchText);
			
			Predicate isSmoke     = builder.equal(root.get("isSmoke"), testCaseSearchDTO.getIsSmoke());
			Predicate isMandatory = builder.equal(root.get("isMandatory"), testCaseSearchDTO.getIsMandatory());
			Predicate priority    = testCaseSearchDTO.getIsPriority() ? 
					builder.equal(root.get("priority"), testCaseSearchDTO.getPriority()) :
					builder.isNotNull(root.get("priority"));
			
			Predicate exists = null;
			if (testCaseSearchDTO.getModuleId() != null) {
				Root<TestCase> testCaseRoot = root;
				Subquery<Module> moduleSubquery = query.subquery(Module.class);
				Root<Module> moduleRoot = moduleSubquery.from(Module.class);
				moduleSubquery.select(moduleRoot);
				moduleSubquery.where(builder.equal(moduleRoot.get("id"), testCaseSearchDTO.getModuleId()),
						builder.isMember(testCaseRoot, moduleRoot.<Set<TestCase>>get("testCases")));
				exists = builder.exists(moduleSubquery);
			} else {
				Join<TestCase, Module> moduleJoin = root.join("modules");
				exists = builder.isNotNull(moduleJoin.get("id"));
			}
			
			if (testCaseSearchDTO.getType().equals(ALL)) {
				Predicate or  = builder.or(id, name, description, location);
				Predicate all = builder.and(name, exists, priority, isSmoke, isMandatory);
				return all;
			} else if (testCaseSearchDTO.getType().equals(NAME)) {
				return builder.and(name, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(DESCRIPTION)) {
				return builder.and(description, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(LOCATION)) {
				return builder.and(location, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(PRIORITY_ONLY)) {
				return builder.and(priority, exists);
			} else if (testCaseSearchDTO.getType().equals(SMOKE_ONLY)) {
				return builder.and(isSmoke, exists);
			} else if (testCaseSearchDTO.getType().equals(MANDATORY_ONLY)) {
				return builder.and(isMandatory, exists);
			}
			
			return null;
		};
	}
	
}
