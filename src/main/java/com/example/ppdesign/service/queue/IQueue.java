package com.example.ppdesign.service.queue;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public interface IQueue {

    void enqueue(JsonNode message, String topic, String expireAfterMillis);

    JsonNode dequeue();

    int size();

    boolean isFull();

    boolean isEmpty();

    void removeExpiredItems();

}
