package com.example.backendspringboot.service;

import com.example.backendspringboot.dto.ItemDTO;
import com.example.backendspringboot.dto.TransactionDTO;
import com.example.backendspringboot.entity.Transaction;
import com.example.backendspringboot.entity.TransactionItem;
import com.example.backendspringboot.repository.ItemRepository;
import com.example.backendspringboot.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> {
                    Set<ItemDTO> itemDTOs = transaction.getTransactionItems().stream()
                            .map(transactionItem -> new ItemDTO(
                                    transactionItem.getItem().getId(),
                                    transactionItem.getItem().getName(),
                                    transactionItem.getItem().getDescription(),
                                    transactionItem.getItem().getPrice(),
                                    transactionItem.getQuantity(),
                                    transactionItem.getTotalPrice()
                            ))
                            .collect(Collectors.toSet());
                    double totalAmount = transaction.getTransactionItems().stream()
                            .mapToDouble(TransactionItem::getTotalPrice)
                            .sum();

                    return new TransactionDTO(
                            transaction.getId(),
                            transaction.getUser().getId(),
                            transaction.getTransactionDate(),
                            itemDTOs,
                            totalAmount
                    );
                })
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Set<ItemDTO> itemDTOs = transaction.getTransactionItems().stream()
                .map(transactionItem -> new ItemDTO(
                        transactionItem.getItem().getId(),
                        transactionItem.getItem().getName(),
                        transactionItem.getItem().getDescription(),
                        transactionItem.getItem().getPrice(),
                        transactionItem.getQuantity(),
                        transactionItem.getTotalPrice()
                ))
                .collect(Collectors.toSet());

        double totalAmount = transaction.getTransactionItems().stream()
                .mapToDouble(TransactionItem::getTotalPrice)
                .sum();

        return new TransactionDTO(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getTransactionDate(),
                itemDTOs,
                totalAmount
        );
    }

}