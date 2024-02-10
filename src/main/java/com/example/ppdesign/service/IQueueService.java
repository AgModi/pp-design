package com.example.ppdesign.service;

import com.example.ppdesign.dto.response.EmpCreateResp;

import java.util.List;

public interface IQueueService<T> {

    EmpCreateResp enqueueData(T object);

    EmpCreateResp enqueueData(List<T> objectList);

    T dequeueData();
}
