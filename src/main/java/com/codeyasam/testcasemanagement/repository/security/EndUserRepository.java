package com.codeyasam.testcasemanagement.repository.security;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.testcasemanagement.domain.security.EndUser;

public interface EndUserRepository extends PagingAndSortingRepository<EndUser, Long> {
	Optional<EndUser> findByUsername(String username);
}
