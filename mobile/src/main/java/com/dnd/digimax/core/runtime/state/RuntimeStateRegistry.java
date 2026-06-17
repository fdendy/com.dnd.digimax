package com.dnd.digimax.core.runtime.state;

import java.util.HashSet;
import java.util.Set;

public class RuntimeStateRegistry {

    private final Set<RuntimeStateObserver> observers =
            new HashSet<>();

    public void register(
            RuntimeStateObserver observer) {

        observers.add(observer);
    }

    public void unregister(
            RuntimeStateObserver observer) {

        observers.remove(observer);
    }

    public void notifyObservers(
            RuntimeStateEvent event) {

        for (RuntimeStateObserver observer : observers) {
            observer.onStateChanged(event);
        }
    }
}