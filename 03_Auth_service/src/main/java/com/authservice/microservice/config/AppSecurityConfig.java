package com.authservice.microservice.config;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.authservice.microservice.serviceImpl.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

    
    private final UserDetailsServiceImpl detailsServiceImpl;

   
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(detailsServiceImpl);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		
		 return authConfig.getAuthenticationManager() ;
	}
	
	
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		 
		 httpSecurity.csrf().disable()
		 .authorizeHttpRequests(auth -> auth

	                // Public endpoints - koi bhi access kar sake
	            		.requestMatchers(
	            			    "/auth/**"
	            			).permitAll()

	                // Swagger endpoints
	                .requestMatchers(
	                    "/swagger-ui.html",
	                    "/swagger-ui/**",
	                    "/v3/api-docs/**"
	                ).permitAll()

	                // Actuator endpoints
	                .requestMatchers(
	                    "/actuator/**"
	                ).permitAll()

	                // Baaki sab authenticated hona chahiye
	                .anyRequest().authenticated()
	            )

	            // Session Management - STATELESS
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(
	                    SessionCreationPolicy.STATELESS
	                )
	            )

	            // Authentication Provider set karo
	            .authenticationProvider(authenticationProvider());

	       

		 return httpSecurity.build();
	 }

}
