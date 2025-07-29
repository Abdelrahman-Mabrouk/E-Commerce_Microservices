package com.E_commerce_Microservices.wallet_service.service;

import com.E_commerce_Microservices.wallet_service.entity.Transaction;
import com.E_commerce_Microservices.wallet_service.entity.Wallet;
import com.E_commerce_Microservices.wallet_service.repositort.TransactionRepository;
import com.E_commerce_Microservices.wallet_service.repositort.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TransactionService {
    @Autowired
    TransactionRepository repo;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    TransactionRepository transactionRepository;
    public List<Transaction> getTransactions(long walletId) {
        if(!walletRepository.findById(walletId).isPresent()){
           throw new RuntimeException("wallet not found");
        }
        List<Transaction>transactions  =  repo.findByWalletId(walletId);
        return transactions;
    }
    public void depositTransaction(Wallet wallet, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("deposit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setWallet(wallet);
        transactionRepository.save(transaction);
    }
    public void withdrawTransaction(Wallet wallet, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("withdraw");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setWallet(wallet);
        transactionRepository.save(transaction);
    }


}
