package com.example.ppdesign.service.producer;

public interface IProducer<T> {
    void produce(T obj);

    T remove();
}
