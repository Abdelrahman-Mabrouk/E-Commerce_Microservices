package com.E_Commerce_Microservices.shop_service.repository;

import com.E_Commerce_Microservices.shop_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}