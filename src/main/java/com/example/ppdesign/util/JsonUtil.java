package com.example.ppdesign.util;

import com.example.ppdesign.exception.EventParsingException;
import com.example.ppdesign.exception.PojoParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Don't annotate with @Component
@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return mapper.convertValue(obj, JsonNode.class);
        } catch (Exception e) {
            log.error("Error while parsing object {}. Error is {}", obj, e);
            throw new PojoParsingException(e.getMessage());
        }
    }

    public static <T> T getObject(JsonNode node, Class<T> clazz) {
        try {
            return mapper.readValue(node.toString(), clazz);
        } catch (JsonProcessingException ex) {
            log.error("Error while parsing json {} for clazz {} . Error is {}", node, clazz, ex);
            throw new EventParsingException(ex.getMessage());
        }
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
        String result = "";
        try {
            return jsonNode.get(key).textValue();
        } catch (Exception e) {
            log.error("Error while parsing node {} for key {} . Error is {}", jsonNode, key, e);
            throw new EventParsingException(e.getMessage());
        }
    }


}
