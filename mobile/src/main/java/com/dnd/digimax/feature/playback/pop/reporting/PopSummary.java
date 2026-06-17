package com.dnd.digimax.feature.playback.pop.reporting;

public class PopSummary {

    private String contentId;

    private int impressions;

    private long totalDuration;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(
            String contentId) {

        this.contentId = contentId;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(
            int impressions) {

        this.impressions = impressions;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(
            long totalDuration) {

        this.totalDuration = totalDuration;
    }

    public void addImpression() {

        impressions++;
    }

    public void addDuration(
            long duration) {

        totalDuration += duration;
    }
}