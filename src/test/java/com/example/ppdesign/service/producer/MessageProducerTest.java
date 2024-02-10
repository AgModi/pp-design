package com.example.ppdesign.service.producer;

import com.example.ppdesign.dto.EmployeeDto;
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
        List<EmployeeDto> employeeDtos = TestDataGenerator.createMockEmployeesData(5);
        employeeDtos.forEach(emp -> messageProducer.produce(JsonUtil.getJson(emp)));
    }

}
