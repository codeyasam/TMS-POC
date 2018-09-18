package com.codeyasam.testcasemanagement.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codeyasam.testcasemanagement.domain.security.EndUser;
import com.codeyasam.testcasemanagement.domain.security.Role;

public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8788886332362956783L;
	
	private EndUser endUser;
	private List<Role> roles;
	
	public UserDetailsImpl(EndUser endUser, List<Role> roles) {
		this.endUser = endUser;
		this.roles = roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
		return authorities;
	}

	@Override
	public String getPassword() {
		return endUser.getPassword();
	}

	@Override
	public String getUsername() {
		return endUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
