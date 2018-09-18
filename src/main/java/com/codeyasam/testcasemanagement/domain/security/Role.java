package com.codeyasam.testcasemanagement.domain.security;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codeyasam.testcasemanagement.domain.Auditable;

@Entity
@Table(name="ROLE")
public class Role extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String role;
	
	@OneToMany(mappedBy="role")
	private Set<EndUserRole> endUserRoles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<EndUserRole> getEndUserRoles() {
		return endUserRoles;
	}

	public void setEndUserRoles(Set<EndUserRole> endUserRoles) {
		this.endUserRoles = endUserRoles;
	}
	
}
