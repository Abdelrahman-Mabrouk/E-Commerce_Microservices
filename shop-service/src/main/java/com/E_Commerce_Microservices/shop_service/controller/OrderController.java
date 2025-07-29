package com.E_Commerce_Microservices.shop_service.controller;

import com.E_Commerce_Microservices.shop_service.entity.Order;
import com.E_Commerce_Microservices.shop_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private  OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Long cartId) {
        return ResponseEntity.ok(orderService.createOrderFromCart(cartId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

     @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long orderId,
                                              @RequestParam String status) {
        return ResponseEntity.ok(orderService.changeStatus(orderId, status));
    }
    @PutMapping("/{orderId}/pay")
    public ResponseEntity<String> pay(@PathVariable Long orderId,
                                      @RequestParam Long userId) {
        return ResponseEntity.ok(orderService.payForOrder(orderId, userId));
    }

}
