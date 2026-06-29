package com.authservice.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerLoginResponse {

	private String token;
	private String refreshToken;
	private CustomerInfoDto customerInfoDto;

}
