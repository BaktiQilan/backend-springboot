package com.example.backendspringboot.controller;

import com.example.backendspringboot.dto.ApiResponse;
import com.example.backendspringboot.dto.CartDTO;
import com.example.backendspringboot.dto.NotFoundDTO;
import com.example.backendspringboot.dto.TransactionDTO;
import com.example.backendspringboot.exception.CartNotFoundException;
import com.example.backendspringboot.exception.NoItemsInCartException;
import com.example.backendspringboot.service.CartService;
import com.example.backendspringboot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CartService cartService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getAllTransactions() {
        try {
            List<TransactionDTO> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "Transactions retrieved successfully", transactions));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while retrieving transactions", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        TransactionDTO transactionDTO = transactionService.getTransactionById(id);
        if (transactionDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundDTO(false, HttpStatus.NOT_FOUND.value(), "Data Transaction Not Found"));
        }
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<TransactionDTO>> checkout(Authentication authentication) {
        ApiResponse<TransactionDTO> response = cartService.checkout(authentication);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode()))
                .body(response);
    }
}
