package com.dnd.digimax.feature.playback.session;

public class PlaybackSession {

    private final String sessionId;

    private final long startedAt;

    public PlaybackSession(
            String sessionId) {

        this.sessionId = sessionId;
        this.startedAt = System.currentTimeMillis();
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getStartedAt() {
        return startedAt;
    }
}