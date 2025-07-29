package com.E_Commerce_Microservices.shop_service.service;


import com.E_Commerce_Microservices.shop_service.entity.Cart;
import com.E_Commerce_Microservices.shop_service.entity.CartItem;
import com.E_Commerce_Microservices.shop_service.feignInterfaces.InventoryClient;
import com.E_Commerce_Microservices.shop_service.repository.CartItemRepository;
import com.E_Commerce_Microservices.shop_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    InventoryClient inventoryClient;


    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    public Cart addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if (quantity > inventoryClient.getStock(productId)) {throw new RuntimeException("Quantity is greater than product.getQuantity");}
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setCart(cart);

        cart.getItems().add(item);
        cartItemRepository.save(item);

        return cart;
    }

   public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public List<Cart> getAllCarts() {
      return cartRepository.findAll();
    }
}
