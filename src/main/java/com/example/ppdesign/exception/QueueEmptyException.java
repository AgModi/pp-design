package com.example.ppdesign.exception;

public class QueueEmptyException extends RuntimeException{
    public QueueEmptyException(String message) {
        super(message);
    }
}
