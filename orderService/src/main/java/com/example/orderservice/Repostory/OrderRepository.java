package com.example.orderservice.Repostory;

import com.example.orderservice.Repostory.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);

    Iterable<OrderEntity> findByUserId(String UserId);

}
