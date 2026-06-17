package com.dnd.digimax.feature.playback.state;

import com.dnd.digimax.feature.playback.event.PlaybackEvent;

public class PlaybackStateMachine {

    private PlaybackState currentState =
            PlaybackState.IDLE;

    public PlaybackState getState() {

        return currentState;
    }

    public PlaybackEvent transition(
            PlaybackState targetState) {

        PlaybackState previous =
                currentState;

        currentState =
                targetState;

        return new PlaybackEvent(
                previous,
                targetState);
    }
}