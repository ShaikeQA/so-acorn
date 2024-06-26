package org.alfabank.soacorn.steps.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JsonPretty {

    ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String pretty(String json) throws JsonProcessingException {
        return objectMapper.writeValueAsString(objectMapper.readValue(json, Object.class));
    }

}
