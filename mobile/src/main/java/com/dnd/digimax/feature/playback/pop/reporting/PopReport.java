package com.dnd.digimax.feature.playback.pop.reporting;

import java.util.List;

public class PopReport {

    private long generatedAt;

    private PopAnalytics analytics;

    private List<PopSummary> summaries;

    public long getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(
            long generatedAt) {

        this.generatedAt =
                generatedAt;
    }

    public PopAnalytics getAnalytics() {
        return analytics;
    }

    public void setAnalytics(
            PopAnalytics analytics) {

        this.analytics =
                analytics;
    }

    public List<PopSummary> getSummaries() {
        return summaries;
    }

    public void setSummaries(
            List<PopSummary> summaries) {

        this.summaries =
                summaries;
    }
}