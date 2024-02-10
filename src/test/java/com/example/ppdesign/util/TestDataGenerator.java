package com.example.ppdesign.util;

import com.example.ppdesign.dto.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {

    public static List<Employee> createMockEmployeesData(int countOfEmployees) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < countOfEmployees; i++) {
            Employee employee = new Employee(i, new String("Emp "+i), new Random().nextInt(100000, 500000));
            employees.add(employee);
        }
        return employees;
    }
}
