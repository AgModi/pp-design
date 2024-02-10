package com.example.ppdesign.service;

import com.example.ppdesign.constants.Constants;
import com.example.ppdesign.service.consumer.IConsumer;
import com.example.ppdesign.service.queue.IQueue;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProdConsBootService {

    private static List<IConsumer> consumerBeans = new ArrayList<>();

    private static final Map<String, List<IConsumer>> topicWiseConsumerBeanMap = new HashMap<>();

    //private static List<IConsumer> priorityList =

    private static List<IQueue> queueBeans = new ArrayList<>();

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        Map<String, IConsumer> consumerMap = context.getBeansOfType(IConsumer.class);
        for (IConsumer consumer : consumerMap.values()) {
            List<String> currentConsTopics = consumer.getTopicsToListen();
            for (String topic : currentConsTopics) {
                List<IConsumer> consumersInMapForTopic = topicWiseConsumerBeanMap.get(topic);
                if (consumersInMapForTopic == null) {
                    List<IConsumer> newConsumersList = new ArrayList<>();
                    newConsumersList.add(consumer);
                    topicWiseConsumerBeanMap.put(topic, newConsumersList);
                } else {
                    consumersInMapForTopic.add(consumer);
                    Collections.sort(consumersInMapForTopic, (o1,o2) -> {
                        List<Class<? extends IConsumer>> consmerToProcessFirst = o1.getClassesToProcessAfter();
                        if (consmerToProcessFirst.size() == 0) {
                            return -1;
                        } else if (consmerToProcessFirst.contains(o2)){
                            return 1;
                        } else {
                            return 0;
                        }
                    });
                }
            }
            consumerBeans.add(consumer);
        }

        System.out.println(consumerBeans);
        System.out.println(topicWiseConsumerBeanMap);

        Map<String, IQueue> queueMap = context.getBeansOfType(IQueue.class);
        queueMap.values().stream().forEach(queue -> queueBeans.add(queue));
        System.out.println(queueBeans);
    }

    public static void pollQueues() {
        queueBeans.forEach(
                iQueue -> {
                    while (iQueue.size() != 0) {
                        JsonNode node = iQueue.dequeue();
                        String nodeTopic = JsonUtil.getStringValueFromJson(node, Constants.TOPIC);

                    }
                }
        );
    }
}
