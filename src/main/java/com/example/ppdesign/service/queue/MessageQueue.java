package com.example.ppdesign.service.queue;

import com.example.ppdesign.constants.Constants;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
@Slf4j
public class MessageQueue implements IQueue{
    private static LinkedList<JsonNode> queue = new LinkedList<>();

    private static LinkedList<JsonNode> deadLetter = new LinkedList<>();

    private static Map<JsonNode, Long> eventArrivedTimeMap = new HashMap<>();

    //TODO Guava library to remove TTL
    @Override
    public synchronized void enqueue(JsonNode message, String topic, String expireAfterMillis) {
        if (isFull()) {
            //TODO exception handling
            log.error("Queue is full. Can't sent message {}", message);
        }
        ((ObjectNode)message).put(Constants.TOPIC, topic);
        ((ObjectNode)message).put(Constants.EXPIRE_AFTER_MILLIS, expireAfterMillis);
        queue.add(message);
        eventArrivedTimeMap.put(message, System.currentTimeMillis());
    }

    @Override
    public synchronized JsonNode dequeue() {
        if (isEmpty()) {
            //TODO exception handling
            log.error("Queue is empty. Can't dequeue");
            return null;
        }
        return queue.removeFirst();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isFull() {
        return queue.size() == Constants.QUEUE_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }

    @Override
    public void removeExpiredItems() {
        removeExpiredItemsAndFillDeadLetter();
    }

    public static void removeExpiredItemsAndFillDeadLetter() {
        for (int i = 0; i < queue.size(); ) {
            JsonNode node = queue.get(i);
            Long expireAfterMillis = Long.parseLong(JsonUtil.getStringValueFromJson(node, Constants.EXPIRE_AFTER_MILLIS));
            Long nodeCreatedAt = eventArrivedTimeMap.get(node);
            if (System.currentTimeMillis() - nodeCreatedAt >= expireAfterMillis) {
                deadLetter.add(node);
                queue.remove(node);
            } else {
                i++;
            }
        }
    }
}
