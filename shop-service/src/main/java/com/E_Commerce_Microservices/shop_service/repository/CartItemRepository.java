package com.E_Commerce_Microservices.shop_service.repository;

import com.E_Commerce_Microservices.shop_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
