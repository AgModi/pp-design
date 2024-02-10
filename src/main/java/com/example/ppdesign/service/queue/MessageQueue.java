package com.example.ppdesign.service.queue;

import com.example.ppdesign.constants.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MessageQueue implements IQueue{
    private LinkedList<JsonNode> queue = new LinkedList<>();

    @Override
    public synchronized boolean enqueue(JsonNode message) {
        System.out.println("Current thread :" + Thread.currentThread().getName());
        if (queue.size() == Constants.QUEUE_SIZE) {
            return false;
        }
        queue.add(message);
        return true;
    }

    @Override
    public synchronized JsonNode dequeue() {
        if (queue.size() == 0) {
            return null;
        }
        return queue.removeLast();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
