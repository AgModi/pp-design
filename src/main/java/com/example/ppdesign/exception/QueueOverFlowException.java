package com.example.ppdesign.exception;

public class QueueOverFlowException extends RuntimeException{
    public QueueOverFlowException(String message) {
        super(message);
    }
}
