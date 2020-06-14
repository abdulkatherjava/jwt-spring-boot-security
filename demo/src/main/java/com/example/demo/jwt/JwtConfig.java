package com.example.demo.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

@ConfigurationProperties(prefix = "application.jwt")
@Configuration
public class JwtConfig {
	private String secretKey;
	private String tokenPrefix;
	private String tokenExpirationDays;
	
	public JwtConfig() {

	}
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getTokenPrefix() {
		return tokenPrefix;
	}
	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}
	public String getTokenExpirationDays() {
		return tokenExpirationDays;
	}
	public void setTokenExpirationDays(String tokenExpirationDays) {
		this.tokenExpirationDays = tokenExpirationDays;
	}
	
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}
	
	
}
