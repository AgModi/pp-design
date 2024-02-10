package com.example.ppdesign.service;

import com.example.ppdesign.dto.EmployeeDto;
import com.example.ppdesign.dto.response.EmpCreateResp;
import com.example.ppdesign.service.producer.IProducer;
import com.example.ppdesign.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService implements IQueueService<EmployeeDto>{

    @Autowired
    @Qualifier("messageProducer")
    private IProducer iProducer;

    public void sendEmployees(List<EmployeeDto> employeeDtos) {
        employeeDtos.stream().forEach(emp -> {
            iProducer.produce(JsonUtil.getJson(emp));
        });
    }

    @Override
    public EmpCreateResp enqueueData(EmployeeDto object) {
        iProducer.produce(JsonUtil.getJson(object));
        return new EmpCreateResp("Successfull pushing of employee data of emp id " + object.getId());
    }

    @Override
    public EmpCreateResp enqueueData(List<EmployeeDto> objectList) {
        for (EmployeeDto employeeDto : objectList) {
            iProducer.produce(JsonUtil.getJson(employeeDto));
        }
        return new EmpCreateResp("Successfully pushing of employees data ");
    }

    @Override
    public EmployeeDto dequeueData() {
        return JsonUtil.getObject((JsonNode)iProducer.remove(), EmployeeDto.class);
    }
}
