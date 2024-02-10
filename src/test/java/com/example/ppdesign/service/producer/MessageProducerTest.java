package com.example.ppdesign.service.producer;

import com.example.ppdesign.dto.Employee;
import com.example.ppdesign.service.queue.IQueue;
import com.example.ppdesign.service.queue.MessageQueue;
import com.example.ppdesign.util.JsonUtil;
import com.example.ppdesign.util.TestDataGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MessageProducerTest {

    @Mock
    private MessageQueue messageQueue;

    @InjectMocks
    private MessageProducer messageProducer;

    @Test
    public void testSendMessage() {
        List<Employee> employees = TestDataGenerator.createMockEmployeesData(5);
        employees.forEach(emp -> messageProducer.send(JsonUtil.getJson(emp)));
    }

}
