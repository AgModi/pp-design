package com.example.ppdesign.service;

import com.example.ppdesign.dto.Employee;
import com.example.ppdesign.service.producer.MessageProducer;
import com.example.ppdesign.util.JsonUtil;
import com.example.ppdesign.util.TestDataGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testCreateEmployees() {
        List<Employee> employees = TestDataGenerator.createMockEmployeesData(5);
        Assert.assertEquals(5, employees.size());
    }

    @Test
    public void testSendEmployees() {
        List<Employee> employees = TestDataGenerator.createMockEmployeesData(5);
        employeeService.sendEmployees(employees);
        employees.forEach(emp -> {
            JsonNode jsonNode = JsonUtil.getJson(emp);
            Mockito.verify(messageProducer, Mockito.times(1)).send(jsonNode);
        });
    }
}
