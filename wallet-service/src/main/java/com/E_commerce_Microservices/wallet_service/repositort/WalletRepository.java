package com.E_commerce_Microservices.wallet_service.repositort;

import com.E_commerce_Microservices.wallet_service.entity.Users;
import com.E_commerce_Microservices.wallet_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
        Optional<Wallet> findByUser(Users user);
        Optional<Wallet> findByUserId(Long userId);
}
