package com.example.backendspringboot.controller;

import com.example.backendspringboot.dto.ApiResponse;
import com.example.backendspringboot.dto.CartDTO;
import com.example.backendspringboot.entity.Cart;
import com.example.backendspringboot.entity.CartItem;
import com.example.backendspringboot.entity.Item;
import com.example.backendspringboot.exception.CartNotFoundException;
import com.example.backendspringboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartDTO>> getCart(Authentication authentication) {
        ApiResponse<CartDTO> response = cartService.getCartForCurrentUser(authentication);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode()))
                .body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartItem>> addItemToCart(@RequestParam Long itemId, @RequestParam int quantity, Authentication authentication) {
        ApiResponse<CartItem> response = cartService.addItemToCart(itemId, quantity, authentication);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
    }
}
