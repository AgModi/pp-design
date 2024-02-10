package com.example.ppdesign.service.producer;

import com.example.ppdesign.service.queue.IQueue;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer implements IProducer<JsonNode>{

    @Value("${topic.employee.expire.after.millis}")
    public static String EXPIRE_AFTER_MILLIS;

    @Value("${exchange.topic}")
    private static String TOPIC;

    @Autowired
    @Qualifier("messageQueue")
    private IQueue queue;

    @Override
    public void produce(JsonNode message) {
        queue.enqueue(message, TOPIC, EXPIRE_AFTER_MILLIS);
    }

    @Override
    public JsonNode remove() {
        return queue.dequeue();
    }
}
