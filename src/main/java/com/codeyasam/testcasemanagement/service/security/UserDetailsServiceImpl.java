package com.codeyasam.testcasemanagement.service.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codeyasam.testcasemanagement.domain.security.EndUser;
import com.codeyasam.testcasemanagement.domain.security.Role;
import com.codeyasam.testcasemanagement.repository.security.EndUserRepository;
import com.codeyasam.testcasemanagement.repository.security.RoleRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private EndUserRepository endUserRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserDetailsServiceImpl(EndUserRepository endUserRepository, RoleRepository roleRepository) {
		this.endUserRepository = endUserRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EndUser> endUser = endUserRepository.findByUsername(username);
		if (!endUser.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		List<Role> roles = roleRepository.findByEndUserRolesEndUserId(endUser.get().getId());
		return new UserDetailsImpl(endUser.get(), roles);
	}

}
