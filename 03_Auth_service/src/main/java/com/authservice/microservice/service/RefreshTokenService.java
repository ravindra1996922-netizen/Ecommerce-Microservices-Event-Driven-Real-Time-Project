package com.authservice.microservice.service;

import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.entity.RefreshToken;

public interface RefreshTokenService {

	RefreshToken createRefreshToken(Customer customer);

	RefreshToken verifyRefreshToken(String token);

	void deleteRefreshToken(Customer customer);
}
