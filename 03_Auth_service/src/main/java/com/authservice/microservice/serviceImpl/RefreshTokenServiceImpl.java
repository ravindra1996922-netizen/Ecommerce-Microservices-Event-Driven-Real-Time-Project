package com.authservice.microservice.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.entity.RefreshToken;
import com.authservice.microservice.exception.AuthServiceExecption;
import com.authservice.microservice.repository.RefreshTokenRepository;
import com.authservice.microservice.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${jwt.refresh-token-expiry}")
	Long refreshTokenExpireIn;

	@Override
	public RefreshToken createRefreshToken(Customer customer) {

		if (refreshTokenRepository.existsByUser(customer)) {

			refreshTokenRepository.deleteByUser(customer);
		}
		RefreshToken refreshToken = RefreshToken.builder().token(UUID.randomUUID().toString())
				.expiryDate(Instant.now().plusMillis(refreshTokenExpireIn)).customer(customer)

				.build();
		RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
		return savedToken;

	}

	@Override
	public RefreshToken verifyRefreshToken(String token) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> {
			return new AuthServiceExecption("Token not found", HttpStatus.NOT_FOUND, "TOKEN_NOT_FOUND");
		});

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {

			refreshTokenRepository.delete(refreshToken);

			throw new AuthServiceExecption("REFRESH_TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED,
					"REFRESH_TOKEN_EXPIRED_ERR_CODE"

			);
		}

		return refreshToken;
	}

	@Override
	public void deleteRefreshToken(Customer customer) {
		// TODO Auto-generated method stub

		refreshTokenRepository.deleteByUser(customer);
	}

}
