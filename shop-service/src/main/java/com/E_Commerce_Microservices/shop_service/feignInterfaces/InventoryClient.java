package com.E_Commerce_Microservices.shop_service.feignInterfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("/products/{productId}/price")
    double getProductPrice(@PathVariable("productId") Long productId);

    @PutMapping("/inventory/stock/reduce/{productId}")
    void reduceStock(@PathVariable("productId") Long productId,
                     @RequestParam("quantity") int quantity);

    @GetMapping("/inventory/stock/{productId}")
   int getStock(@PathVariable Long productId);
}
