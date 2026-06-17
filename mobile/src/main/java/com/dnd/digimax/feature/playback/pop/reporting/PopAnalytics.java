package com.dnd.digimax.feature.playback.pop.reporting;

public class PopAnalytics {

    private int totalImpressions;

    private long totalPlaybackDuration;

    private int uniqueContents;

    public int getTotalImpressions() {
        return totalImpressions;
    }

    public void setTotalImpressions(
            int totalImpressions) {

        this.totalImpressions =
                totalImpressions;
    }

    public long getTotalPlaybackDuration() {
        return totalPlaybackDuration;
    }

    public void setTotalPlaybackDuration(
            long totalPlaybackDuration) {

        this.totalPlaybackDuration =
                totalPlaybackDuration;
    }

    public int getUniqueContents() {
        return uniqueContents;
    }

    public void setUniqueContents(
            int uniqueContents) {

        this.uniqueContents =
                uniqueContents;
    }
}