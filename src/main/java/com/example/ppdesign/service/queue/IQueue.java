package com.example.ppdesign.service.queue;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public interface IQueue {

    boolean enqueue(JsonNode message);

    JsonNode dequeue();

    int size();

}
