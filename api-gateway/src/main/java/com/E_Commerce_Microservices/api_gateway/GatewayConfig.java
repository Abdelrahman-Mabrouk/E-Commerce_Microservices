package com.E_Commerce_Microservices.api_gateway;



import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // 🟢 Wallet Service
                .route("wallet-service", r -> r
                        .path("/wallet/**")
                        .uri("lb://WALLET-SERVICE"))

                // 🟢 Inventory Service
                .route("inventory-service", r -> r
                        .path("/inventory/**")
                        .uri("lb://INVENTORY-SERVICE"))

                // 🟢 Order Service
                .route("order-service", r -> r
                        .path("/orders/**")
                        .uri("lb://SHOP-SERVICE"))

                // 🟢 Shop Service
                .route("shop-service", r -> r
                        .path("/shop/**")
                        .uri("lb://SHOP-SERVICE"))

                // 🟢 User Service
                .route("user-service", r -> r
                        .path("/user/**")
                        .uri("lb://WALLET-SERVICE"))

                // 🟢 Cart Service
                .route("cart-service", r -> r
                        .path("/cart/**")
                        .uri("lb://SHOP-SERVICE"))

                .build();
    }
}
