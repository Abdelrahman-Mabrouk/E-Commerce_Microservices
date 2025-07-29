package com.E_commerce_Microservices.wallet_service.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
@Table
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @OneToOne
    private Users user;
    public Wallet() {}
    public Wallet(Long id, Double balance, Users user) {
        this.id = id;
        this.balance = balance;
        this.user = user;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public Double getBalance() {
        return balance;
    }
    public void setUser(Users user) {
        this.user = user;
    }
    public Users getUser() {
        return user;
    }

}
