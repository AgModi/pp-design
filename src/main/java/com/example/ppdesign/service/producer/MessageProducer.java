package com.example.ppdesign.service.producer;

import com.example.ppdesign.constants.Constants;
import com.example.ppdesign.service.queue.IQueue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer implements IProducer<JsonNode>{

    private static String topic = "employee";

    @Autowired
    private IQueue queue;

    @Override
    public void send(JsonNode message) {
        ((ObjectNode)message).put(Constants.TOPIC, topic);
        boolean isMessageQueued = queue.enqueue(message);
        System.out.println("Message is : "+ (isMessageQueued? "Queued" : "Not queued") + "\n" + "Message :"+ message);
    }
}
