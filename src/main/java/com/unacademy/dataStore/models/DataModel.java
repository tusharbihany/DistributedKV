package com.unacademy.dataStore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class DataModel {
    // Key vs Value
    private Map<String, String> dataMap;
    // Key vs TTL
    private Map<String, Long> timeMap;

    public DataModel() {
        dataMap = new ConcurrentHashMap<String, String>();
        timeMap = new ConcurrentHashMap<String, Long>();
    }
}
