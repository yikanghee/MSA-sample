package com.example.orderservice.service.impl;

import com.example.orderservice.Repostory.OrderRepository;
import com.example.orderservice.Repostory.entity.OrderEntity;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSerivceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDeatails) {

        orderDeatails.setOrderId(UUID.randomUUID().toString());
        orderDeatails.setTotalPrice(orderDeatails.getQty() * orderDeatails.getUnitPrice());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = modelMapper.map(orderDeatails, OrderEntity.class);

        orderRepository.save(orderEntity);

        OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

}
