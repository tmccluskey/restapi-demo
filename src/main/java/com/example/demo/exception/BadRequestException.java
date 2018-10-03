package com.example.demo.exception;

public class BadRequestException extends Exception {

    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}