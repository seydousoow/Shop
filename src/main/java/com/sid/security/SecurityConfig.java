package com.sid.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		/*
		 * 1- Disable csrf
		 * 2- Deactivate sessions
		 * 3- Require authentication on every request
		 * 4- Add Filter
		 */
		http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.requestMatcher(PathRequest.toStaticResources().atCommonLocations()).authorizeRequests().antMatchers(HttpMethod.GET).permitAll()
				.and()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
