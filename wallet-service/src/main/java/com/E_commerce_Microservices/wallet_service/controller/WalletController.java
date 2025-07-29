package com.E_commerce_Microservices.wallet_service.controller;

import com.E_commerce_Microservices.wallet_service.entity.Transaction;
import com.E_commerce_Microservices.wallet_service.entity.Wallet;
import com.E_commerce_Microservices.wallet_service.service.TransactionService;
import com.E_commerce_Microservices.wallet_service.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/create/{userId}")
    public Wallet createWallet(@PathVariable Long userId) {
        return walletService.createWallet(userId);
    }
    @PostMapping("/deposit/{userId}/{amount}")
    public Wallet deposit(@PathVariable Long userId, @PathVariable double amount) {
        return walletService.deposit(userId, amount);
    }
    @PutMapping ("/withdraw/{userId}/{amount}")
    public Wallet withdraw(@PathVariable Long userId, @PathVariable  double amount) {
        return walletService.withdraw(userId, amount);
    }

    @GetMapping("/balance/{userId}")
    public double getBalance(@PathVariable Long userId) {
        return walletService.getBalance(userId);
    }
    @DeleteMapping("/delete/{userId}")
    public String  deleteWallet(@PathVariable Long userId) {
       return walletService.deleteWallet(userId);
    }
    @GetMapping("/{walletId}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long walletId) {
     return    transactionService.getTransactions(walletId);
    }
    @GetMapping("/allWallets")
    public List<Wallet> getAllWallets() {
      return   walletService.getAllWallet();
    }
    @PostMapping("/pay/{userId}")
    public ResponseEntity<String> pay(@PathVariable Long userId, @RequestParam double amount) {
        boolean success = walletService.pay(userId, amount);
        if (success) {
            return ResponseEntity.ok("Payment successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
    }

}
