package com.example.ppdesign.service.producer;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public interface IProducer<T> {

    void send(T obj);
}
