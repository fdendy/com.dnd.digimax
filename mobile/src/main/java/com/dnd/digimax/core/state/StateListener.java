package com.dnd.digimax.core.state;

public interface StateListener {

    void onStateChanged(
            StateEvent event
    );
}