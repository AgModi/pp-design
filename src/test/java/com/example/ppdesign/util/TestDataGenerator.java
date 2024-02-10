package com.example.ppdesign.util;

import com.example.ppdesign.dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {

    public static List<EmployeeDto> createMockEmployeesData(int countOfEmployees) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (int i = 0; i < countOfEmployees; i++) {
            EmployeeDto employeeDto = new EmployeeDto(i, new String("Emp "+i), new Random().nextInt(100000, 500000));
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }
}
