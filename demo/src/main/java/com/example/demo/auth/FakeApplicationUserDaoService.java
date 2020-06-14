package com.example.demo.auth;

import static com.example.demo.security.ApplicationUserRole.ADMIN;
import static com.example.demo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.example.demo.security.ApplicationUserRole.STUDENT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository("Hard intialization of user details")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}

	private List<ApplicationUser> getApplicationUsers() {
		
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser(
						STUDENT.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"raja",
						true,
						true,
						true,
						true
				),
				new ApplicationUser(
						ADMIN.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"admin",
						true,
						true,
						true,
						true
				),
				new ApplicationUser(
						ADMINTRAINEE.getGrantedAuthorities(),
						passwordEncoder.encode("password"),
						"trainee",
						true,
						true,
						true,
						true
				)
			);
		
		return applicationUsers;
	}
}
