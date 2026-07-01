package com.orderservice.microservices.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.dto.OrderDto;
import com.orderservice.microservices.entity.CartItemEntity;
import com.orderservice.microservices.entity.OrderEntity;
import com.orderservice.microservices.entity.OrderItemsEntity;
import com.orderservice.microservices.entity.OrderStatus;
import com.orderservice.microservices.exception.OrderServiceException;
import com.orderservice.microservices.feignClient.ProductServiceClient;
import com.orderservice.microservices.mapper.CartMapper;
import com.orderservice.microservices.mapper.OrderMapper;
import com.orderservice.microservices.repository.CartRepository;
import com.orderservice.microservices.repository.OrderRepository;
import com.orderservice.microservices.response.ApiResponse;
import com.orderservice.microservices.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;
	private final ProductServiceClient productServiceClient;
	private final CartMapper cartMapper;
	private final OrderMapper orderMapper;
	private final ModelMapper modelMapper;

	@Override
	public OrderDto placeOrder(Integer customerId) {

		List<CartItemEntity> listCart = cartRepository.findByCustomerId(customerId);
		if (listCart.isEmpty()) {
			throw new OrderServiceException("order not completed ", HttpStatus.BAD_REQUEST);
		}

		List<OrderItemsEntity> listOrderItemEntity = listCart.stream().map(cartItem -> {
			ApiResponse<CartItemDto> apiResponse = productServiceClient.getProductById(cartItem.getProductId());

			if (apiResponse.getStatusCode() >= 400) {
				throw new OrderServiceException("cant get response from producct service ", HttpStatus.BAD_GATEWAY);
			}

			CartItemDto data = apiResponse.getData();

			if (data.getQuantity() < cartItem.getQuantity()) {

				throw new OrderServiceException("qunatity not avilable", HttpStatus.BAD_REQUEST);
			} else {
				CartItemDto cartItemDto = cartMapper.convertIntoCartItemDto(cartItem);

				return modelMapper.map(cartItemDto, OrderItemsEntity.class);
			}
		}).toList();
		Double totalAmount = listCart.stream().mapToDouble(item -> item.getQuantity() * item.getProductPrice()).sum();

		OrderEntity orderEntity = OrderEntity.builder().products(listOrderItemEntity).status(OrderStatus.ORDER_PLACED)
				.customerId(customerId).totalAmount(totalAmount).build();

		OrderEntity savedOrderEntiy = orderRepository.save(orderEntity);

		return orderMapper.convetIntoOrderDto(orderEntity);
	}

	@Override
	public List<OrderDto> getAllorderDetails(Integer CustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDto UpadteOrderStatus(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
