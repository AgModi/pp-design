package com.example.ppdesign.service.consumer;

import com.example.ppdesign.dto.EmployeeDto;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EmpReceiverB implements IConsumer{

    private static final List<String> TOPICS = Arrays.asList("employee");

    private static final List<Class<? extends IConsumer>> PROCESS_AFTER = new ArrayList<>();

    private static final int RETRY_COUNT = 3;

    @Override
    public boolean consume(JsonNode node) {
        System.out.println("In consumer B for message :" + node);
        return processMessage(node);
    }

    @Override
    public List<String> getTopicsToListen() {
        return TOPICS;
    }

    @Override
    public List<Class<? extends IConsumer>> getClassesToProcessAfter() {
        return PROCESS_AFTER;
    }

    @Override
    public int getRetryCount() {
        return RETRY_COUNT;
    }

    private boolean processMessage(JsonNode node) {
        EmployeeDto employeeDto = JsonUtil.getObject(node, EmployeeDto.class);
        if (employeeDto != null) {
            System.out.println("Resurting true from consumer B for message "+ node);
            return true;
        }
        System.out.println("Resurting false from consumer B for message "+ node);
        return false;
    }
}
