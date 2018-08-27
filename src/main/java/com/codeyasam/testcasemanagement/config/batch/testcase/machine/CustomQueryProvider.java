package com.codeyasam.testcasemanagement.config.batch.testcase.machine;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeyasam.testcasemanagement.domain.TestCase;
import com.codeyasam.testcasemanagement.domain.specification.TestCaseSpecification;
import com.codeyasam.testcasemanagement.dto.TestCaseSearchDTO;

public class CustomQueryProvider implements JpaQueryProvider {

	private EntityManager entityManager;
	private TestCaseSearchDTO testCaseSearchDTO;
	
	@Autowired
	public CustomQueryProvider(TestCaseSearchDTO testCaseSearchDTO) {
		this.testCaseSearchDTO = testCaseSearchDTO;
	}
	
	@Override
	public Query createQuery() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TestCase> criteriaQuery = criteriaBuilder.createQuery(TestCase.class);
		Root<TestCase> root = criteriaQuery.from(TestCase.class);
		criteriaQuery.select(root);
		Predicate restrictions = TestCaseSpecification.defineSpecification(testCaseSearchDTO).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery);
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
