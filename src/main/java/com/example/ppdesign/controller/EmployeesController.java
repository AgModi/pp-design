package com.example.ppdesign.controller;

import com.example.ppdesign.constants.Constants;
import com.example.ppdesign.dto.EmployeeDto;
import com.example.ppdesign.dto.response.EmpCreateResp;
import com.example.ppdesign.service.IQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeesController {

    @Autowired
    @Qualifier("employeeService")
    private IQueueService iQueueService;

    @PostMapping(value = Constants.ENQUEUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmpCreateResp createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return iQueueService.enqueueData(employeeDto);
    }

    @PostMapping(value = Constants.ENQUEUE_BULK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmpCreateResp createEmployees(@RequestBody @Valid List<EmployeeDto> employeeList) {
        return iQueueService.enqueueData(employeeList);
    }

    @DeleteMapping
    public @ResponseBody EmployeeDto deleteEmployee() {
        return (EmployeeDto)iQueueService.dequeueData();
    }
}
