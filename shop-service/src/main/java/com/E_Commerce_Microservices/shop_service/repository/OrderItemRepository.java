package com.E_Commerce_Microservices.shop_service.repository;

import com.E_Commerce_Microservices.shop_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
