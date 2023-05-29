package com.tajne.test.svbackend.util;



import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class StringResourceDataProvider {

    protected static final ConcurrentMap<String, String> RESOURCE_MAP_CACHE = new ConcurrentHashMap();

    public String getResource(String fileName) {
        if (RESOURCE_MAP_CACHE.containsKey(fileName)) {
            return RESOURCE_MAP_CACHE.get(fileName);
        } else {
            String stringResource = loadResource(fileName);
            RESOURCE_MAP_CACHE.put(fileName, stringResource);
            return stringResource;
        }
    }

    private String loadResource(String resourceName) {
        try {
            Resource resource = new ClassPathResource(resourceName);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            log.error(ioException.getMessage(), ioException);
            return null;
        }
    }
}

