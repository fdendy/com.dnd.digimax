package com.dnd.digimax.core.state;

import java.util.ArrayList;
import java.util.List;

public class StateBus {

    //================================================
    // SINGLETON
    //================================================

    private static final StateBus instance =
            new StateBus();

    public static StateBus get() {

        return instance;
    }

    //================================================
    // LISTENERS
    //================================================

    private final List<StateListener> listeners =
            new ArrayList<>();

    //================================================
    // REGISTER
    //================================================

    public void register(
            StateListener listener
    ) {

        if (listener == null)
            return;

        if (listeners.contains(listener))
            return;

        listeners.add(listener);
    }

    //================================================
    // UNREGISTER
    //================================================

    public void unregister(
            StateListener listener
    ) {

        listeners.remove(listener);
    }

    //================================================
    // EMIT
    //================================================

    public void emit(
            StateEvent event
    ) {

        for (StateListener listener
                : listeners) {

            listener.onStateChanged(event);
        }
    }

}