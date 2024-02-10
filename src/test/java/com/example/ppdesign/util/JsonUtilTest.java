package com.example.ppdesign.util;

import com.example.ppdesign.dto.EmployeeDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class JsonUtilTest {

    @Test
    public void testJsonToObject() {
        List<EmployeeDto> employeeDtos = TestDataGenerator.createMockEmployeesData(1);
        JsonNode jsonNode = JsonUtil.getJson(employeeDtos.get(0));
        log.info("{}", JsonUtil.getObject(jsonNode, EmployeeDto.class));
    }
}
