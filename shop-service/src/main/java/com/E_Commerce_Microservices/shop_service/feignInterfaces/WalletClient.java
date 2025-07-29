package com.E_Commerce_Microservices.shop_service.feignInterfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "WALLET-SERVICE")
public interface WalletClient {

    @PostMapping("/wallet/pay/{userId}")
    ResponseEntity<String> pay(@PathVariable("userId") Long userId,
                                             @RequestParam("amount") double amount);
}
