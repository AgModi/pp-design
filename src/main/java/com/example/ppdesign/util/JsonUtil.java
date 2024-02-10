package com.example.ppdesign.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

//Don't annotate with @Component
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getJson(Object obj) {
        if (obj == null) {
            return null;
            //Throw exceptioption
        }
        return mapper.convertValue(obj, JsonNode.class);
    }

    public static <T> T getObject(JsonNode node, Class<T> clazz) {
        try {
            return mapper.readValue(node.toString(), clazz);
        } catch (JsonProcessingException ex) {
            System.out.println("Error while parsing json :" + node);
        }
        return null;
    }

    public static <T> List<T> getObjectList(JsonNode json, Class<T> clazz) {
        List<T> objects = new ArrayList<>();
        if (json.isArray()) {
            for (JsonNode jsonObj : json) {
                objects.add(getObject(jsonObj, clazz));
            }
        }
        return objects;
    }

    public static String getStringValueFromJson(JsonNode jsonNode, String key) {
        return String.valueOf(jsonNode.get(key));
    }


}
