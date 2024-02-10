package com.example.ppdesign.service.consumer;

import com.example.ppdesign.constants.Constants;
import com.example.ppdesign.dto.EmployeeDto;
import com.example.ppdesign.exception.ListnerFailureException;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class EmpReceiverC implements IConsumer{

    private static final List<String> TOPICS = Arrays.asList(Constants.TOPIC_EMPLOYEE);

    private static final List<Class<? extends IConsumer>> PROCESS_AFTER = Arrays.asList(EmpReceiverA.class, EmpReceiverB.class);

    private static final int RETRY_COUNT = 3;

    @Override
    public boolean consume(JsonNode node) {
        log.info("In consumer C for message {}", node);
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
            log.info("Returting true from consumer C for message {}", node);
            return true;
        }
        log.info("Returting false from consumer C for message {}", node);
        throw new ListnerFailureException("Listsner C failed to process object" + node);
    }
}
