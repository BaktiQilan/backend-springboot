package com.example.backendspringboot.service;

import com.example.backendspringboot.dto.*;
import com.example.backendspringboot.entity.*;
import com.example.backendspringboot.exception.CartNotFoundException;
import com.example.backendspringboot.exception.InsufficientStockException;
import com.example.backendspringboot.exception.NoItemsInCartException;
import com.example.backendspringboot.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public ApiResponse<CartDTO> getCartForCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user"));

        if (cart.getCartItems().isEmpty()) {
            return new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Cart is Empty",
                    null
            );
        }

        Set<ItemDTO> itemDTOS = cart.getCartItems().stream()
                .map(cartItem -> new ItemDTO(
                        cartItem.getItem().getId(),
                        cartItem.getItem().getName(),
                        cartItem.getItem().getDescription(),
                        cartItem.getItem().getPrice(),
                        cartItem.getQuantity(),
                        cartItem.getTotalPrice()
                ))
                .collect(Collectors.toSet());
        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        CartDTO cartDTO = new CartDTO(cart.getId(), itemDTOS, totalAmount);

        ApiResponse<CartDTO> response = new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Cart retrieved successfully",
                cartDTO
        );

        return response;
        }

    @Transactional
    public ApiResponse<CartItem> addItemToCart(Long itemId, int quantity, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (quantity <= 0) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), "Quantity must be greater than zero", null);
        }

        if (quantity > item.getStock()) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), "Requested quantity exceeds available stock", null);
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().equals(item))
                .findFirst();

        CartItem cartItem;
        if (existingCartItem.isPresent()) {

            cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;


            if (newQuantity > item.getStock()) {
                return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), "Requested quantity exceeds available stock", null);
            }

            cartItem.setQuantity(newQuantity);
        } else {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);

            if (quantity > item.getStock()) {
                return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), "Requested quantity exceeds available stock", null);
            }

            cart.getCartItems().add(cartItem);
        }

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        return new ApiResponse<>(true, HttpStatus.OK.value(), "Item added to cart", cartItem);
    }

    public ApiResponse<TransactionDTO> checkout(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CartNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new NoItemsInCartException("No items available in cart for user: " + username);
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDateTime.now());

        Set<TransactionItem> transactionItems = new HashSet<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Item item = cartItem.getItem();
            int quantity = cartItem.getQuantity();

            if (item.getStock() < quantity) {
                throw new InsufficientStockException("Not enough stock for item: " + item.getName());
            }

            item.setStock(item.getStock() - quantity);
            itemRepository.save(item);

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setItem(item);
            transactionItem.setQuantity(quantity);
            transactionItem.setTransaction(transaction);
            transactionItems.add(transactionItem);
        }

        transaction.setTransactionItems(transactionItems);
        Transaction savedTransaction = transactionRepository.save(transaction);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        double totalAmount = savedTransaction.getTransactionItems().stream()
                .mapToDouble(transactionItem -> transactionItem.getQuantity() * transactionItem.getItem().getPrice())
                .sum();

        Set<ItemDTO> itemDTOs = savedTransaction.getTransactionItems().stream()
                .map(transactionItem -> {
                    Item item = transactionItem.getItem();
                    return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), transactionItem.getQuantity(), transactionItem.getTotalPrice());
                })
                .collect(Collectors.toSet());

        TransactionDTO transactionDTO = new TransactionDTO(
                savedTransaction.getId(),
                user.getId(),
                savedTransaction.getTransactionDate(),
                itemDTOs,
                totalAmount
        );

        return new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Checkout successful",
                transactionDTO
        );
    }
}
