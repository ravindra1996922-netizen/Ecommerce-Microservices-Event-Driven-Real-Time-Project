package com.authservice.microservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.microservice.dto.CustomerInfoDto;
import com.authservice.microservice.dto.CustomerLoginRequest;
import com.authservice.microservice.dto.CustomerLoginResponse;
import com.authservice.microservice.dto.CustomerRefreshTokenRequestDto;
import com.authservice.microservice.dto.CustomerRegisterInfoDto;
import com.authservice.microservice.entity.Role;
import com.authservice.microservice.repository.RoleRepo;
import com.authservice.microservice.response.ApiResponse;
import com.authservice.microservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class CustomerController {

	private final CustomerService customerService;
	private final RoleRepo repo;
//========================================================================================================================

	@PostMapping("/customer")
	public ResponseEntity<ApiResponse<CustomerInfoDto>> addCustomer(
			@Valid @RequestBody CustomerRegisterInfoDto customerRegisterInfoDto) {

		CustomerInfoDto saveCustomer = customerService.saveCustomer(customerRegisterInfoDto, "CUSTOMER");
		ApiResponse<CustomerInfoDto> response = new ApiResponse<>();
		response.setData(saveCustomer);
		response.setMsg("user created");
		response.setStatusCode(201);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

//========================================================================================================================

	@PostMapping("/deliveryy")
	public ResponseEntity<ApiResponse<CustomerInfoDto>> adddelivery(
			@RequestBody CustomerRegisterInfoDto customerRegisterInfoDto) {

		CustomerInfoDto saveCustomer = customerService.saveCustomer(customerRegisterInfoDto, "DELIVERY");
		ApiResponse<CustomerInfoDto> response = new ApiResponse<>();
		response.setData(saveCustomer);
		response.setMsg("user created");
		response.setStatusCode(201);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	// ==================================================================================================================
	@PostMapping("/store")
	public ResponseEntity<ApiResponse<CustomerInfoDto>> addStoreOwner(
			@RequestBody CustomerRegisterInfoDto customerRegisterInfoDto) {

		CustomerInfoDto saveCustomer = customerService.saveCustomer(customerRegisterInfoDto, "STORE_OWNER");
		ApiResponse<CustomerInfoDto> response = new ApiResponse<>();
		response.setData(saveCustomer);
		response.setMsg("user created");
		response.setStatusCode(201);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

//==================================================================================================================
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<CustomerLoginResponse>> login(
			@Valid @RequestBody CustomerLoginRequest customerLoginRequest) {

		CustomerLoginResponse login = customerService.login(customerLoginRequest);

		ApiResponse<CustomerLoginResponse> response = new ApiResponse<CustomerLoginResponse>();
		response.setData(login);
		response.setMsg("login successfull");
		response.setStatusCode(200);

		return new ResponseEntity<ApiResponse<CustomerLoginResponse>>(response, HttpStatus.OK);

	}

//==================================================================================================================
	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		log.info(role.getRole(), role.getRoleId(), role.getCustomers() + "");

		Role role2 = repo.save(role);

		return role2;

	}
// ==================================================================================================================

	@PostMapping("/refresh-token")
	public ResponseEntity<ApiResponse<CustomerLoginResponse>> refreshToken(
			@Valid @RequestBody CustomerRefreshTokenRequestDto refreshTokenRequest) {

		log.info("Refresh token request received");

		CustomerLoginResponse loginResponse = customerService.refreshToken(refreshTokenRequest);

		ApiResponse<CustomerLoginResponse> response = ApiResponse.<CustomerLoginResponse>builder().statusCode(201)
				.data(loginResponse).msg("new Jwt Token genrated through refresh token").build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
// ==================================================================================================================

	@PostMapping("/signout")
	public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("X-email") String email) {

		customerService.logout(email);

		ApiResponse<String> response = ApiResponse.<String>builder().msg("Logout Sucessfully").statusCode(200).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// ==================================================================================================================

}
