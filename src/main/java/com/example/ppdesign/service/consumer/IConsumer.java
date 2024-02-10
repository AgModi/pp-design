package com.example.ppdesign.service.consumer;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface IConsumer {

    public boolean consume(JsonNode node);

    public List<String> getTopicsToListen();

    public List<Class<? extends IConsumer>> getClassesToProcessAfter();

    public int getRetryCount();

}
