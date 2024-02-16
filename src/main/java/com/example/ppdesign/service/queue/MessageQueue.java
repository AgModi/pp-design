package com.example.ppdesign.service.queue;

import com.example.ppdesign.dto.MessageDto;
import com.example.ppdesign.exception.QueueEmptyException;
import com.example.ppdesign.exception.QueueOverFlowException;
import com.example.ppdesign.service.exchange.IExchange;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@Slf4j
//@Lazy
public class MessageQueue implements IQueue{
    private static LinkedList<MessageDto> queue = new LinkedList<>();

    private static LinkedList<MessageDto> deadLetter = new LinkedList<>();

    @Value("${queue.size}")
    public Integer queueSize;

    @Autowired
    @Qualifier("exchangeMQ")
    private IExchange iExchange;

    //TODO Guava library to remove TTL
    @Override
    public synchronized void enqueue(JsonNode message, String topic, String expireAfterMillis) {
        if (isFull()) {
            log.error("Queue is full. Can't sent message {}", message);
            throw new QueueOverFlowException("Queue is full. Can't add new node");
        }

        MessageDto messageDto = new MessageDto(topic, Long.parseLong(expireAfterMillis), System.currentTimeMillis(), message);

        /*((ObjectNode)message).put(Constants.TOPIC, topic);
        ((ObjectNode)message).put(Constants.EXPIRE_AFTER_MILLIS, expireAfterMillis);*/
        queue.add(messageDto);
        //eventArrivedTimeMap.put(messageDto, System.currentTimeMillis());
        log.info("Added message in queue {}. Queue size is {}", message, queue.size());
        iExchange.exchange();
    }

    @Override
    public synchronized MessageDto dequeue() {
        if (isEmpty()) {
            log.error("Queue is empty. Can't dequeue");
            throw new QueueEmptyException("Queue is empty. Can't dequeue");
        }

        log.info("Removing message from queue. Queue size for now is {}", queue.size());
        MessageDto node = queue.removeFirst();
        //eventArrivedTimeMap.remove(node);
        log.info("Removed message from queue {}. Queue size finally is {}", node, queue.size());
        return node;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isFull() {
        return queue.size() == queueSize;
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
            MessageDto node = queue.get(i);
            Long expireAfterMillis = node.getExpireAfterMillis();
            Long nodeCreatedAt = node.getEventCreatonTime();
            if (System.currentTimeMillis() - nodeCreatedAt >= expireAfterMillis) {
                deadLetter.add(node);
                queue.remove(node);
            } else {
                i++;
            }
        }
    }
}
