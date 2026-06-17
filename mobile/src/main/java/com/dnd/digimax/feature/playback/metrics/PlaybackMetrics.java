package com.dnd.digimax.feature.playback.metrics;

public class PlaybackMetrics {

    private long totalPlayTime;

    private int completedContent;

    private int playbackErrors;

    public void addPlayTime(
            long duration) {

        totalPlayTime += duration;
    }

    public void incrementCompletedContent() {

        completedContent++;
    }

    public void incrementPlaybackErrors() {

        playbackErrors++;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public int getCompletedContent() {
        return completedContent;
    }

    public int getPlaybackErrors() {
        return playbackErrors;
    }
}