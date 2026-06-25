package com.apigateway.microservice.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.apigateway.microservice.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebAuthenticationFilter implements WebFilter {
	
	

	private final JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		log.info("WebAuthenticationFilter running");

		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

		log.info(bearerToken+" ====token");
		log.info(headers+"   ===header");
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			
			return chain.filter(exchange);
		}
		String token = bearerToken.substring(7);

		Boolean tokenValid = jwtUtil.isTokenValid(token);

		if (!tokenValid) {
			return chain.filter(exchange);
		}

		String email = jwtUtil.extractUsername(token);
		List<String> roles = jwtUtil.exrtactRoles(token);
		Integer userId = jwtUtil.extractUserId(token);

		List<SimpleGrantedAuthority> authorities = roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
			ServerHttpRequest modifiedRequest = request.mutate().header("X-email", email)
					.header("X-CustomerId", String.valueOf(userId)).header("X-roles", String.join(",", roles)).build();

			return chain.filter(exchange.mutate().request(modifiedRequest).build())
					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
		} catch (Exception e) {
			e.getMessage();
		}

		return null;
		
	}

}
