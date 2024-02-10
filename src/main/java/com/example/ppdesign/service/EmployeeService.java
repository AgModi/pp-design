package com.example.ppdesign.service;

import com.example.ppdesign.dto.Employee;
import com.example.ppdesign.service.producer.MessageProducer;
import com.example.ppdesign.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EmployeeService {

    @Autowired
    private MessageProducer messageProducer;

    /*public List<Employee> createEmployees(int countOfEmployees) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < countOfEmployees; i++) {
            Employee employee = new Employee(i, new String("Emp "+i), new Random().nextInt(100000, 500000));
            employees.add(employee);
        }
        return employees;
    }*/

    public void sendEmp(Employee employee) {
        messageProducer.send(JsonUtil.getJson(employee));
    }

    public void sendEmployees(List<Employee> employees) {
        employees.stream().forEach(emp -> {
            messageProducer.send(JsonUtil.getJson(emp));
        });
    }
}
