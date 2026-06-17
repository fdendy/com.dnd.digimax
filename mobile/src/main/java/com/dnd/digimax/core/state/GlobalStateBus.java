package com.dnd.digimax.core.state;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GlobalStateBus implements StatePublisher {

    private static final GlobalStateBus INSTANCE =
            new GlobalStateBus();

    private final StateSnapshot snapshot =
            new StateSnapshot();

    private final Map<String, List<StateSubscriber>> subscribers =
            new ConcurrentHashMap<>();

    private GlobalStateBus() {
    }

    public static GlobalStateBus getInstance() {
        return INSTANCE;
    }

    @Override
    public void publish(String key, Object value) {

        snapshot.put(key, value);

        List<StateSubscriber> list =
                subscribers.get(key);

        if (list == null) {
            return;
        }

        for (StateSubscriber subscriber : list) {
            subscriber.onStateChanged(key, value);
        }
    }

    public void subscribe(
            String key,
            StateSubscriber subscriber
    ) {

        subscribers
                .computeIfAbsent(
                        key,
                        k -> new CopyOnWriteArrayList<>()
                )
                .add(subscriber);

        // sticky latest state
        if (snapshot.contains(key)) {
            subscriber.onStateChanged(
                    key,
                    snapshot.get(key)
            );
        }
    }

    public void unsubscribe(
            String key,
            StateSubscriber subscriber
    ) {

        List<StateSubscriber> list =
                subscribers.get(key);

        if (list != null) {
            list.remove(subscriber);
        }
    }

    public Object getState(String key) {
        return snapshot.get(key);
    }
}