package com.codeyasam.testcasemanagement.repository.security;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.security.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	
	List<Role> findByEndUserRolesEndUserId(long id);
	
}
