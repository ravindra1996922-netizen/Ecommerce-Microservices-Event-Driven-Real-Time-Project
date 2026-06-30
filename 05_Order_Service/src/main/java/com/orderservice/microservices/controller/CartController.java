package com.orderservice.microservices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.dto.CartRequestDto;
import com.orderservice.microservices.response.ApiResponse;
import com.orderservice.microservices.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {

	private final CartService cartService;

	@PostMapping("/addcart")
	public ResponseEntity<ApiResponse<CartItemDto>> addProduct(@RequestHeader("X-CustomerId") Integer customerId,
			@RequestBody CartRequestDto cartRequestDto) {

		log.info(cartRequestDto.getProductId() + "cartController" + customerId);
		CartItemDto cartItemDto = cartService.addcart(customerId, cartRequestDto);
		ApiResponse<CartItemDto> apiResponse = ApiResponse.<CartItemDto>builder().data(cartItemDto).statusCode(201)
				.msg("item added in cart sucessfully").build();
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/getCartBycustomerid")
	public ResponseEntity<ApiResponse<List<CartItemDto>>> getAllCartItems(
			@RequestHeader("X-CustomerId") Integer customerID) {

		List<CartItemDto> allCartItems = cartService.getAllCartItems(customerID);
		ApiResponse<List<CartItemDto>> apiResponse = ApiResponse.<List<CartItemDto>>builder().data(allCartItems)
				.msg("cart fetch succesfully").statusCode(200).build();

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}

	@PutMapping("/UpdateCartItemQuantity/{cartId}")
	public ResponseEntity<ApiResponse<CartItemDto>> UpdateCartItemQuantity(
			@RequestHeader("X-CustomerId") Integer customerId, @PathVariable Integer cartId,
			@Valid @RequestBody CartRequestDto cartRequestDto) {

		CartItemDto updatedCartItem = cartService.UpdateCartItemQuantity(customerId, cartId, cartRequestDto);

		ApiResponse<CartItemDto> response = ApiResponse.<CartItemDto>builder()
				.msg("cart item quantity updated successfull").statusCode(200).data(updatedCartItem).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// =====================================================================================================

	@DeleteMapping("/deleteAllCart")
	public ResponseEntity<ApiResponse<String>> deleteAllCart(@RequestHeader("X-CustomerId") Integer customerId) {

		String deleteAllCart = cartService.deleteAllCart(customerId);

		ApiResponse<String> response = ApiResponse.<String>builder().msg("all cart deleted successfull").statusCode(200)
				.data(deleteAllCart).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// =====================================================================================================

	@DeleteMapping("/removeCartItem/{cartId}")
	public ResponseEntity<ApiResponse<String>> removeCartItem(@PathVariable Integer cartId) {

		String removeCartItem = cartService.removeCartItem(cartId);

		ApiResponse<String> response = ApiResponse.<String>builder().msg("cart item deleted successfull")
				.statusCode(200).data(removeCartItem).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
