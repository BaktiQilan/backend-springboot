package com.example.backendspringboot.exception;

public class NoItemsInCartException extends RuntimeException {
    public NoItemsInCartException(String message) {
        super(message);
    }
}