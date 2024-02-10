package com.example.ppdesign.exception;

public class MessageNotProducedException extends RuntimeException{
    public MessageNotProducedException(String message) {
        super(message);
    }
}
