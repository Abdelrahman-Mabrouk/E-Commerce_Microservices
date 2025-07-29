package com.E_Commerce_Microservices.shop_service.repository;

import com.E_Commerce_Microservices.shop_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {}
