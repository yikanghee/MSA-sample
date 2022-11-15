package com.example.orderservice.service;

import com.example.orderservice.Repostory.entity.OrderEntity;
import com.example.orderservice.dto.OrderDto;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDeatails);

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
