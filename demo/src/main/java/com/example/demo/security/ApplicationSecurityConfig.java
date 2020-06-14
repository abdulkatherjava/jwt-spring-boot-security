package com.example.demo.security;

import static com.example.demo.security.ApplicationUserRole.STUDENT;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
			ApplicationUserService applicationUserService,
			SecretKey secretKey,
			JwtConfig jwtConfig
			) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
			.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
//			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
			.anyRequest()
			.authenticated();
		
		/**
		 * The below code is be used for FORM login
		 */
			/*.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/courses", true)
			.and()
			.rememberMe()//default is two weeks
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
				.key("veryconfidentials")
			 .and()
			.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "remember-me")
				.logoutSuccessUrl("/login");*/
					
			
						
			/**
			 * The blow code is for Basic Authentication
			 */
//			.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
	 
	
/**
 * The will use when do not use the Dao configuration
 */
	/*@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails abdulUser = User.builder()
				.username("abdul")
				.password(passwordEncoder.encode("password"))
				.authorities(STUDENT.getGrantedAuthorities())
//				.roles(STUDENT.name())getApplicationUsers
				.build();
		
		UserDetails lindaUser = User.builder()
				.username("linda")
				.password(passwordEncoder.encode("password123"))
//				.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails tomUser = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("password123"))
//				.roles(ADMINTRAINEE.name())
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(
				abdulUser,
				lindaUser,
				tomUser
				);
	}
*/
	
}
