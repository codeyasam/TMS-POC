package com.codeyasam.testcasemanagement.domain.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.testcasemanagement.domain.Module;
import com.codeyasam.testcasemanagement.domain.ModuleTestCase;
import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;

public class TestCaseSpecification {
	
	public static enum SearchType {
		ALL("all"),
		NAME("name"),
		DESCRIPTION("description"),
		LOCATION("location"),
		PRIORITY_ONLY("priorityOnly"),
		SMOKE_ONLY("smokeOnly"),
		MANDATORY_ONLY("mandatoryOnly");
		
		private String type;
		
		SearchType(String type) {
			this.type = type;
		}
		
		public String type() {
			return type;
		}
	}
	
	public static Specification<TestCase> defineSpecification(TestCaseSearchDTO testCaseSearchDTO) {
		String text = testCaseSearchDTO.getText().trim();
		if (text.isEmpty()) text = "%" + text + "%";
		testCaseSearchDTO.setText(text);
		
		return (root, query, builder) -> {
			final String searchText = testCaseSearchDTO.getText().toLowerCase().trim();
			Predicate id          = builder.like(builder.concat("", root.get("id")), searchText);
			Predicate name        = builder.like(builder.lower(root.get("name")), searchText);
			Predicate description = builder.like(builder.lower(root.get("testCaseAttribute").get("description")), searchText);
			Predicate location    = builder.like(builder.lower(root.get("testCaseAttribute").get("location")), searchText);
			
			Predicate isSmoke     = builder.equal(root.get("testCaseAttribute").get("isSmoke"), testCaseSearchDTO.getIsSmoke());
			Predicate isMandatory = builder.equal(root.get("testCaseAttribute").get("isMandatory"), testCaseSearchDTO.getIsMandatory());
			Predicate priority    = testCaseSearchDTO.getIsPriority() == 1 ? 
					builder.equal(root.get("testCaseAttribute").get("priority"), testCaseSearchDTO.getPriority()) :
					builder.isNotNull(root.get("testCaseAttribute").get("priority"));
			
			Predicate exists = null;
			if (testCaseSearchDTO.getModuleId() != null && testCaseSearchDTO.getModuleId() != 0) {
				Root<TestCase> testCaseRoot = root;
				Join<TestCase, ModuleTestCase> joinedModuleTestCase = testCaseRoot.join("moduleTestCases");
				Path<ModuleTestCase> moduleTestCaseId = joinedModuleTestCase.get("module").get("id");
				Predicate isModuleIdEqual = builder.equal(moduleTestCaseId, testCaseSearchDTO.getModuleId());
				exists = isModuleIdEqual;
			} else {
				Join<TestCase, Module> moduleJoin = root.join("modules");
				exists = builder.isNotNull(moduleJoin.get("id"));
			}
			
			if (testCaseSearchDTO.getType().equals(SearchType.ALL.type())) {
				Predicate or  = builder.or(id, name, description, location);
				Predicate all = builder.and(or, name, exists, priority, isSmoke, isMandatory);
				return all;
			} else if (testCaseSearchDTO.getType().equals(SearchType.NAME.type())) {
				return builder.and(name, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(SearchType.DESCRIPTION.type())) {
				return builder.and(description, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(SearchType.LOCATION.type())) {
				return builder.and(location, exists, priority, isSmoke, isMandatory);
			} else if (testCaseSearchDTO.getType().equals(SearchType.PRIORITY_ONLY.type())) {
				return builder.and(priority, exists);
			} else if (testCaseSearchDTO.getType().equals(SearchType.SMOKE_ONLY.type())) {
				return builder.and(isSmoke, exists);
			} else if (testCaseSearchDTO.getType().equals(SearchType.MANDATORY_ONLY.type())) {
				return builder.and(isMandatory, exists);
			}
			
			return null;
		};
	}
	
}
