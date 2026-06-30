package com.apigateway.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.apigateway.microservice.filter.WebAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final WebAuthenticationFilter authenticationFilter;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {

		log.info("security web filter class running");
		httpSecurity.csrf().disable();
		httpSecurity.formLogin().disable();
		httpSecurity.logout().disable();
		httpSecurity.httpBasic().disable();

		httpSecurity.addFilterAfter(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.authorizeExchange(req -> req.pathMatchers("/auth/**").permitAll()

						.pathMatchers(HttpMethod.GET, "/product/**").hasAnyRole("CUSTOMER", "STORE_OWNER")

						.pathMatchers(HttpMethod.POST, "/product/**").hasRole("STORE_OWNER")

						.pathMatchers(HttpMethod.PUT, "/product/**").hasRole("STORE_OWNER")

						.pathMatchers(HttpMethod.DELETE, "/product/**").hasRole("STORE_OWNER")

						.anyExchange().authenticated());

		return httpSecurity.build();
	}
}