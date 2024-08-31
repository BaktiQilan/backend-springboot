package com.example.backendspringboot.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class TransactionDTO {

    private Long id;
    private Long userId;
    private LocalDateTime transactionDate;
    private Set<ItemDTO> items;
    private double totalAmount;

    // Constructors
    public TransactionDTO(Long id, Long userId, LocalDateTime transactionDate, Set<ItemDTO> items, Double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemDTO> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}