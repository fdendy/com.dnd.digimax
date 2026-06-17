package com.dnd.digimax.core.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateSnapshot {

    private final Map<String, Object> states =
            new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        states.put(key, value);
    }

    public Object get(String key) {
        return states.get(key);
    }

    public boolean contains(String key) {
        return states.containsKey(key);
    }

    public Map<String, Object> getAll() {
        return states;
    }
}