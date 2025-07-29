package com.E_commerce_Microservices.wallet_service.service;

import com.E_commerce_Microservices.wallet_service.entity.Transaction;
import com.E_commerce_Microservices.wallet_service.entity.Users;
import com.E_commerce_Microservices.wallet_service.entity.Wallet;
import com.E_commerce_Microservices.wallet_service.repositort.TransactionRepository;
import com.E_commerce_Microservices.wallet_service.repositort.UserRepository;
import com.E_commerce_Microservices.wallet_service.repositort.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionService transactionService;

    public WalletService() {}
    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }
    public Optional<Wallet> getWallet(Long id) {
        return Optional.ofNullable(walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found")));
    }
    public Wallet createWallet(Long userId){
       Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
       Wallet wallet = new Wallet();
       wallet.setUser(user);
       wallet.setBalance(0.0);
       user.setWallet(wallet);
       return walletRepository.save(wallet);
    }
    @Transactional
    public Wallet deposit(Long userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        transactionService.depositTransaction(wallet,amount);
         return wallet;
    }
    @Transactional
    public Wallet withdraw(Long userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
        transactionService.withdrawTransaction(wallet,amount);
        return wallet;
    }
    public double getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }
    public String deleteWallet(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        walletRepository.delete(wallet);
        return "Wallet deleted";
    }

    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    public boolean pay(Long userId, double amount) {
        withdraw(userId, amount);
        return true;
    }
}
