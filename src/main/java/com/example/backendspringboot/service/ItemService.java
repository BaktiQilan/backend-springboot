package com.example.backendspringboot.service;

import com.example.backendspringboot.entity.Item;
import com.example.backendspringboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    // Other methods to save, update, and delete items
}