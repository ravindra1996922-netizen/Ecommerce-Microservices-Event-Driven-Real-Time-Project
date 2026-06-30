package com.orderservice.microservices.service;

import java.util.List;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.dto.CartRequestDto;
import com.orderservice.microservices.entity.CartItemEntity;

public interface CartService {
	
 public CartItemDto addcart(Integer customerId, CartRequestDto cartRequestDto);
 
 public  List<CartItemDto> getAllCartItems(Integer customerID);
 
 public  CartItemDto  UpdateCartItemQuantity(Integer customerId, Integer cartId,CartRequestDto cartRequestDto);
 
 public String deleteAllCart(Integer customerId);
 
 public String removeCartItem(Integer cartId);

}


