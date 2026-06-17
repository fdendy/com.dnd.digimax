package com.dnd.digimax.core.runtime.state;

public interface RuntimeStateObserver {

    void onStateChanged(
            RuntimeStateEvent event);
}