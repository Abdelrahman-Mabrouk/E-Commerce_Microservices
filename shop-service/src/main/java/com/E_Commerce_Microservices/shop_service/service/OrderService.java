package com.E_Commerce_Microservices.shop_service.service;

import com.E_Commerce_Microservices.shop_service.entity.Cart;
import com.E_Commerce_Microservices.shop_service.entity.CartItem;
import com.E_Commerce_Microservices.shop_service.entity.Order;
import com.E_Commerce_Microservices.shop_service.entity.OrderItem;
import com.E_Commerce_Microservices.shop_service.feignInterfaces.InventoryClient;
import com.E_Commerce_Microservices.shop_service.feignInterfaces.WalletClient;
import com.E_Commerce_Microservices.shop_service.repository.CartRepository;
import com.E_Commerce_Microservices.shop_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    WalletClient walletClient;
    @Autowired
    InventoryClient inventoryClient;


    public Order createOrderFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            double price = inventoryClient.getProductPrice(cartItem.getProductId());
            total += price*cartItem.getQuantity();
            item.setPrice(price);
            item.setOrder(order);
            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        cartRepository.delete(cart);

        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order changeStatus(Long id, String status) {
        Order order = getById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @CircuitBreaker(name = "walletClient", fallbackMethod = "walletFallback")
    public String payForOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        double amount = order.getTotalPrice();

        try {
            safeWalletPay(userId, amount);
        order.setStatus("PAID");
            orderRepository.save(order);
            for (OrderItem item : order.getItems()) {
                safeReduceStock(item.getProductId(), item.getQuantity());
            }
            return  "Payment successful | Order marked as PAID.";
        } catch (Exception e) {
            throw  e ;
        }
    }
    public String walletFallback(Long userId, double amount, Throwable t) {
        return("Wallet service unavailable");
    }

    public String inventoryFallback(Long productId, int quantity, Throwable t) {
        return ("Inventory fallback triggered - stock not reduced");
    }
    @RateLimiter(name = "walletClient", fallbackMethod = "rateLimiterFallback")
    @CircuitBreaker(name = "walletClient", fallbackMethod = "walletFallback")
    @Retry(name = "walletClient", fallbackMethod = "walletFallback")
    public void safeWalletPay(Long userId, double amount) {
        walletClient.pay(userId, amount);
    }

    @RateLimiter(name = "inventoryClient", fallbackMethod = "rateLimiterFallback")
    @CircuitBreaker(name = "inventoryClient", fallbackMethod = "inventoryFallback")
    @Retry(name = "inventoryClient", fallbackMethod = "inventoryFallback")
    public void safeReduceStock(Long productId, int quantity) {
        inventoryClient.reduceStock(productId, quantity);
    }


    public String rateLimiterFallback(Long productId, int quantity, Throwable t) {
        return "Maximum requests exceeded, try again later";
    }
}
