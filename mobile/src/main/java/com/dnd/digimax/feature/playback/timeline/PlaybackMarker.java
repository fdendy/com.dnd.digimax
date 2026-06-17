package com.dnd.digimax.feature.playback.timeline;

public class PlaybackMarker {

    private final String contentId;

    private final long timestamp;

    public PlaybackMarker(
            String contentId) {

        this.contentId = contentId;
        this.timestamp = System.currentTimeMillis();
    }

    public String getContentId() {
        return contentId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}