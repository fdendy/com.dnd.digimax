package com.dnd.digimax.feature.orchestrator.playback;

import com.dnd.digimax.feature.playback.engine.PlaybackEngine;

public class PlaybackOrchestrator {

    private final PlaybackEngine playbackEngine;

    public PlaybackOrchestrator(
            PlaybackEngine playbackEngine) {

        this.playbackEngine =
                playbackEngine;
    }

    public void start() {

        playbackEngine.startSession();

        playbackEngine.startPlayback();
    }

    public void pause() {

        playbackEngine.pausePlayback();
    }

    public void resume() {

        playbackEngine.resumePlayback();
    }

    public void stop() {

        playbackEngine.stopSession();
    }

    public PlaybackEngine getEngine() {

        return playbackEngine;
    }
}