package com.dnd.digimax.core.state;

public interface StatePublisher {
    void publish(String key, Object value);
}