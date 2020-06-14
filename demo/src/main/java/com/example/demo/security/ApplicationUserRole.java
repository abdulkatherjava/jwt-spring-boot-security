package com.example.demo.security;

import static com.example.demo.security.ApplicationUserPermission.COURSE_READ;
import static com.example.demo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.demo.security.ApplicationUserPermission.STUDENT_READ;
import static com.example.demo.security.ApplicationUserPermission.STUDENT_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {
	
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE,COURSE_READ, COURSE_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(STUDENT_READ,COURSE_READ));
	
	private final Set<ApplicationUserPermission> permissions;

	ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toSet());
		
		System.out.println("ROLE_"+this.name());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		
		return permissions;
	}
}
