package com.apigateway.microservice.utils;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	@Value("${jwt.secret}")
	String secretKey;

	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public Integer extractUserId(String token) {
		return extractAllClaims(token).get("userId", Integer.class);

	}

	public List<String> exrtactRoles(String token) {

		return extractAllClaims(token).get("roles", List.class);
	}

	public Boolean isTokenValid(String token) {

		try {

			boolean before = extractAllClaims(token).getExpiration().before(new Date());
			log.info(before+" befor");
			return !before;

		} catch (Exception e) {
			// TODO: handle exception

			e.getMessage();

		}
		return false;
	}

}
