package com.authservice.microservice.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.authservice.microservice.dto.CustomerInfoDto;
import com.authservice.microservice.dto.CustomerLoginRequest;
import com.authservice.microservice.dto.CustomerLoginResponse;
import com.authservice.microservice.dto.CustomerRefreshTokenRequestDto;
import com.authservice.microservice.dto.CustomerRegisterInfoDto;
import com.authservice.microservice.entity.Customer;

public interface CustomerService {

	CustomerInfoDto saveCustomer(CustomerRegisterInfoDto customer, String role);

	CustomerLoginResponse login(CustomerLoginRequest customerLoginRequest);

  CustomerLoginResponse refreshToken(CustomerRefreshTokenRequestDto refreshTokenRequest);

	    void logout(String email);

}
