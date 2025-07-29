package com.E_Commerce_Microservices.shop_service.controller;


import com.E_Commerce_Microservices.shop_service.dto.CartItemRequest;
import com.E_Commerce_Microservices.shop_service.entity.Cart;
import com.E_Commerce_Microservices.shop_service.entity.CartItem;
import com.E_Commerce_Microservices.shop_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.createCart(userId));
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId,
                                          @RequestBody CartItemRequest cartItem) {
        return ResponseEntity.ok(cartService.addToCart(cartId, cartItem.getProductId(),cartItem.getQuantity()));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Cart deleted");
    }
    @GetMapping("/allCarts")
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

}
