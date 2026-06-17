package com.dnd.digimax.feature.playback.event;

import com.dnd.digimax.feature.playback.state.PlaybackState;

public class PlaybackEvent {

    private final PlaybackState previousState;
    private final PlaybackState currentState;

    public PlaybackEvent(
            PlaybackState previousState,
            PlaybackState currentState) {

        this.previousState = previousState;
        this.currentState = currentState;
    }

    public PlaybackState getPreviousState() {
        return previousState;
    }

    public PlaybackState getCurrentState() {
        return currentState;
    }
}