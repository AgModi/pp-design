package com.example.ppdesign.service.exchange;

import com.example.ppdesign.dto.MessageDto;
import com.example.ppdesign.service.consumer.IConsumer;
import com.example.ppdesign.service.queue.IQueue;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ExchangeMQ implements IExchange<JsonNode>{

    private static final Map<String, List<IConsumer>> topicWiseConsumerBeanMap = new HashMap<>();

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
        }
        log.info("{}",topicWiseConsumerBeanMap);

        Map<String, IQueue> queueMap = context.getBeansOfType(IQueue.class);
        queueMap.values().stream().forEach(queue -> queueBeans.add(queue));
        log.info("{}",queueBeans);
    }

    @Override
    public void exchange() {
        Thread thread = new Thread(() -> {
            Map<String, IQueue> queueMap = context.getBeansOfType(IQueue.class);
            queueMap.values().stream().forEach(queue -> {
                synchronized (queue) {
                    MessageDto node = queue.dequeue();
                    String topic = node.getTopic();
                    List<IConsumer> consumerForTopic = topicWiseConsumerBeanMap.get(topic);
                    consumerForTopic.forEach(consumer -> {
                        exchange(consumer, node.getData(), consumer.getRetryCount());
                    });

                    queue.notify();
                }
            });
        });
        thread.start();
    }

    @Override
    public void exchange(JsonNode obj) {
        //Not needed for now
    }

    private void exchange(IConsumer consumer, JsonNode node, int retryCount) {
        boolean isSuccess = false;
        try {
            isSuccess = consumer.consume(node);
        } catch (Exception e) {
            log.error("Exception while exchanging message {}. Exception is {}", node, e);
        }
        if (!isSuccess) {
            if (retryCount > 0) {
                log.debug("Retrying exchange for {} th time for node {}", retryCount, node);
                exchange(consumer, node, --retryCount);
            } else {
                //TODO we won't enqueue message again
                log.error("Retry exhausted for node {}", node);
            }
        }
    }


    @Deprecated
    public static void pollQueues() {
        queueBeans.forEach(
                iQueue -> {
                    while (!iQueue.isEmpty()) {
                        MessageDto node = iQueue.dequeue();
                        String nodeTopic = node.getTopic();

                    }
                }
        );
    }
}
