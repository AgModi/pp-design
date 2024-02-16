package com.example.ppdesign.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    String topic;

    Long expireAfterMillis;

    Long eventCreatonTime;

    JsonNode data;
}
