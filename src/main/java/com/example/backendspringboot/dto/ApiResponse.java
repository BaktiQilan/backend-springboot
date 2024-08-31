package com.example.backendspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"isSuccess", "statusCode", "message", "data"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private boolean isSuccess;
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;

    public ApiResponse(boolean isSuccess, int statusCode, String message, T data) {
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
