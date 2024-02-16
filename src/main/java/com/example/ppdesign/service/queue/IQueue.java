package com.example.ppdesign.service.queue;

import com.example.ppdesign.dto.MessageDto;
import com.fasterxml.jackson.databind.JsonNode;

//@Component
public interface IQueue {

    void enqueue(JsonNode message, String topic, String expireAfterMillis);

    MessageDto dequeue();

    int size();

    boolean isFull();

    boolean isEmpty();

    void removeExpiredItems();

}
