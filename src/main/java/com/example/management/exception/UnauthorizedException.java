package com.example.management.exception;

public class UnauthorizedException extends RuntimeException {
    private String message;
    public UnauthorizedException(String msg) {
        super(msg);
        this.message = msg;
    }
 
}
