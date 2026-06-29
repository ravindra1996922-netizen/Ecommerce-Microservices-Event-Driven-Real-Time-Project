package com.authservice.microservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRefreshTokenRequestDto {
	
	 @NotBlank(message = "Refresh token is required")
	    private String refreshToken;

}
