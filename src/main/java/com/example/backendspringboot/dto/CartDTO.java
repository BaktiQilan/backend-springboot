package com.example.backendspringboot.dto;

import com.example.backendspringboot.entity.Item;

import java.util.Set;

public class CartDTO {

    private Long id;
    private Set<ItemDTO> items;
    private double totalAmount;

    public CartDTO(Long id, Set<ItemDTO> items, double totalAmount) {
        this.id = id;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
