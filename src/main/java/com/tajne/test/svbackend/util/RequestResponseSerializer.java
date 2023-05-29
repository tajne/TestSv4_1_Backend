package com.tajne.test.svbackend.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestResponseSerializer {

    private ObjectMapper buildObjectMapperForSerialization() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());
        objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objMapper.disable(SerializationFeature.WRAP_EXCEPTIONS);
        objMapper.disable(SerializationFeature.INDENT_OUTPUT);
        objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objMapper;
    }

    public String objToString(Object obj) {
        try {
            return buildObjectMapperForSerialization().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Error Trying to serialize object in json format: {}" , obj, e);
        }
        return null;
    }

    public <T> T stringToObj(String inputString, Class<T> responseType) {
        try {
            return new ObjectMapper().readValue(inputString, responseType);
        } catch (Exception e) {
            log.warn("Error during deserialization: {}" , e.getMessage(), e);
        }
        return null;
    }

    public <T> T objConversion(Object object, Class<T> responseType) {
        try {
            return buildObjectMapperForSerialization().convertValue(object, responseType);
        } catch (Exception e) {
            log.warn("Error during objConversion: " + e.getMessage(), e);
        }
        return null;
    }
}
