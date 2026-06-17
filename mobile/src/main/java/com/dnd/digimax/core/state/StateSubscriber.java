package com.dnd.digimax.core.state;

public abstract class StateSubscriber {
    public abstract void onStateChanged(String key, Object value);
}
