package com.authservice.microservice.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.microservice.dto.CustomerInfoDto;
import com.authservice.microservice.dto.CustomerLoginRequest;
import com.authservice.microservice.dto.CustomerLoginResponse;
import com.authservice.microservice.dto.CustomerRegisterInfoDto;
import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.entity.Role;
import com.authservice.microservice.exception.AuthServiceExecption;
import com.authservice.microservice.mapper.CustomerMapper;
import com.authservice.microservice.repository.CustomerRepo;
import com.authservice.microservice.repository.RoleRepo;
import com.authservice.microservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	private final CustomerRepo customerRepo;
	private final CustomerMapper customerMapper;
	private final RoleRepo repo;
	private final PasswordEncoder passwordEncoder;
	private final JwtServiceImpl jwtServiceImpl;

	private final AuthenticationManager authenticationManager;

	@Override
	public CustomerInfoDto saveCustomer(CustomerRegisterInfoDto customerDto,String  role) {

		Customer customer = customerMapper.convertIntoEntity(customerDto);

		Set<Role> roles = customer.getRoles();

		 Role role2 = repo.getRole(role);

		roles.add(role2);

		customer.setRoles(roles);
		String pwd = customer.getPwd();
		String encode = passwordEncoder.encode(pwd);
		customer.setPwd(encode);

		if (customerRepo.existsByEmail(customer.getEmail())) {

			throw new AuthServiceExecption("Email already exist", HttpStatus.CONFLICT, "409");
		}
		Customer customer2 = customerRepo.save(customer);
		
		CustomerInfoDto customerInfoDto= new CustomerInfoDto();
		customerInfoDto.setEmail(customer2.getEmail());
		customerInfoDto.setName(customer2.getName());
		customerInfoDto.setRoles(customer2.getRoles());
		

		return customerInfoDto;
	}

	@Override
	public CustomerLoginResponse login(CustomerLoginRequest loginRequest) {
		log.info(loginRequest.getEmail()+"  "+ loginRequest.getPwd());

		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPwd()));
			log.info(authenticate+"");

		} catch (Exception e) {

			throw new AuthServiceExecption("invalid credentials", HttpStatus.UNAUTHORIZED, "401");
		}

		Customer customer = customerRepo.findByEmail(loginRequest.getEmail());

		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(customer.getEmail());

		Map<String, Object> extraClaim = new HashMap<String, Object>();

		Set<String> roleNames = new HashSet<String>();

		for (Role role : customer.getRoles()) {

			roleNames.add(role.getRole());
		}

		extraClaim.put("userId", customer.getCustomerId());

		extraClaim.put("roles", roleNames);

		String jwtToken = jwtServiceImpl.createJwtToken(extraClaim, userDetails);

		CustomerInfoDto customerInfoDto = CustomerInfoDto.builder().email(customer.getEmail()).name(customer.getName())
				.roles(customer.getRoles()).build();

		CustomerLoginResponse loginResponse = new CustomerLoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setCustomerInfoDto(customerInfoDto);

		return loginResponse;
	}

}
