package com.authservice.microservice.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.authservice.microservice.dto.CustomerLoginRequest;
import com.authservice.microservice.dto.CustomerLoginResponse;
import com.authservice.microservice.dto.CustomerRegisterInfoDto;
import com.authservice.microservice.entity.Customer;

public interface CustomerService {

	
	  Customer saveCustomer(CustomerRegisterInfoDto customer,Integer roleid);
	  

	  
	   
	CustomerLoginResponse   login(CustomerLoginRequest customerLoginRequest);
	   

	
}
