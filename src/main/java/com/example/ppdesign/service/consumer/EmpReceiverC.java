package com.example.ppdesign.service.consumer;

import com.example.ppdesign.dto.Employee;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EmpReceiverC implements IConsumer{

    private static final List<String> topics = Arrays.asList("employee");

    private static final List<Class<? extends IConsumer>> processAfter = Arrays.asList(EmpReceiverA.class, EmpReceiverB.class);

    @Override
    public boolean consume(JsonNode node) {
        System.out.println("In consumer C for message :" + node);
        return processMessage(node);
    }

    @Override
    public List<String> getTopicsToListen() {
        return topics;
    }

    @Override
    public List<Class<? extends IConsumer>> getClassesToProcessAfter() {
        return processAfter;
    }

    private boolean processMessage(JsonNode node) {
        Employee employee = JsonUtil.getObject(node, Employee.class);
        if (employee != null) {
            System.out.println("Resurting true from consumer C for message "+ node);
            return true;
        }
        System.out.println("Resurting false from consumer C for message "+ node);
        return false;
    }
}
