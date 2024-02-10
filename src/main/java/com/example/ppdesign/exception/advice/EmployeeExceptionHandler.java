package com.example.ppdesign.exception.advice;

import com.example.ppdesign.controller.EmployeesController;
import com.example.ppdesign.dto.response.EmpCreateResp;
import com.example.ppdesign.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {EmployeesController.class})
@Slf4j
@Configuration
public class EmployeeExceptionHandler {

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler(QueueOverFlowException.class)
    public EmpCreateResp handleException(QueueOverFlowException exception) {
        return new EmpCreateResp(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler(QueueEmptyException.class)
    public EmpCreateResp handleException(QueueEmptyException exception) {
        return new EmpCreateResp(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PojoParsingException.class)
    public EmpCreateResp handleException(PojoParsingException exception) {
        return new EmpCreateResp(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessageNotProducedException.class)
    public EmpCreateResp handleException(MessageNotProducedException exception) {
        return new EmpCreateResp(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler(ListnerFailureException.class)
    public EmpCreateResp handleException(ListnerFailureException exception) {
        return new EmpCreateResp(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EventParsingException.class)
    public EmpCreateResp handleException(EventParsingException exception) {
        return new EmpCreateResp(exception.getMessage());
    }
}
