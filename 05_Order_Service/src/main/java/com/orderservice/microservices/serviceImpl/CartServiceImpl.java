package com.orderservice.microservices.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.orderservice.microservices.response.ApiResponse;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.dto.CartRequestDto;
import com.orderservice.microservices.entity.CartItemEntity;
import com.orderservice.microservices.exception.OrderServiceException;
import com.orderservice.microservices.feignClient.ProductServiceClient;
import com.orderservice.microservices.mapper.CartMapper;
import com.orderservice.microservices.repository.CartRepository;
import com.orderservice.microservices.repository.OrderRepository;
import com.orderservice.microservices.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final OrderRepository orderRepository;
	private final ProductServiceClient productServiceClient;
	private final CartMapper cartMapper;
	private final CartRepository cartRepository;

	@Override
	public CartItemDto addcart(Integer customerId, CartRequestDto cartRequestDto) {

		Integer productId = cartRequestDto.getProductId();

		ApiResponse<CartItemDto> response = productServiceClient.getProductById(productId);
		CartItemDto getCartProductDto = response.getData();

		getCartProductDto.setCustomerId(customerId);
		getCartProductDto.setProductId(productId);
		getCartProductDto.setQuantity(cartRequestDto.getProductQuantity());

		CartItemEntity convertIntoCartEntity = cartMapper.convertIntoCartEntity(getCartProductDto);

		CartItemEntity saved = cartRepository.save(convertIntoCartEntity);
		CartItemDto convertIntoCartItemDto = cartMapper.convertIntoCartItemDto(saved);
		return convertIntoCartItemDto;
	}

	@Override
	public List<CartItemDto> getAllCartItems(Integer customerID) {
		// TODO Auto-generated method stub
		List<CartItemEntity> allCartItemList = cartRepository.findAll();

		if (allCartItemList.isEmpty()) {
			throw new OrderServiceException("Cart Is Empty", HttpStatus.BAD_REQUEST);
		}
		List<CartItemDto> listCartItemDto = allCartItemList.stream()
				.map(data -> cartMapper.convertIntoCartItemDto(data)).toList();

		return listCartItemDto;
	}

	@Override
	public CartItemDto UpdateCartItemQuantity(Integer customerId, Integer cartId, CartRequestDto cartRequestDto) {

		Optional<CartItemEntity> optional = cartRepository.findById(cartId);
		if (optional.isEmpty()) {
			throw new OrderServiceException("cart item not found", HttpStatus.NOT_FOUND);
		}

		Integer productId = cartRequestDto.getProductId();

		CartItemDto cartItemDto = productServiceClient.getProductById(productId).getData();
		if (cartItemDto.getQuantity() < cartRequestDto.getProductQuantity()) {
			throw new OrderServiceException("stock not availalbe", HttpStatus.BAD_REQUEST);
		}

		CartItemEntity cartItemEntity = optional.get();

		cartItemEntity.setQuantity(cartRequestDto.getProductQuantity());
		CartItemEntity savedCartItemEntity = cartRepository.save(cartItemEntity);

		return cartMapper.convertIntoCartItemDto(savedCartItemEntity);
	}

	@Override
	public String deleteAllCart(Integer customerId) {

		if (customerId == null) {
			throw new OrderServiceException("no customer id present", HttpStatus.BAD_REQUEST);
		}

		boolean existsById = cartRepository.existsById(customerId);
		if (!existsById) {
			throw new OrderServiceException("cart not present", HttpStatus.NOT_FOUND);
		}

		cartRepository.deleteByCustomerId(customerId);

		return "deleted successfull";
	}

	@Override
	public String removeCartItem(Integer cartId) {
		// TODO Auto-generated method stub
		if (cartId == null) {
			throw new OrderServiceException("no cart id present", HttpStatus.BAD_REQUEST);
		}

		boolean existsById = cartRepository.existsById(cartId);
		if (!existsById) {
			throw new OrderServiceException("cart not present", HttpStatus.NOT_FOUND);
		}

		cartRepository.deleteById(cartId);

		return "deleted successfull";

	}
	

}
