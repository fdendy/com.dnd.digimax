package com.dnd.digimax.feature.playback.pop.reconciliation.model;

public class ReconciliationResult {

    private String contentId;

    private int localCount;

    private int remoteCount;

    private int delta;

    private ReconciliationState state;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(
            String contentId) {

        this.contentId = contentId;
    }

    public int getLocalCount() {
        return localCount;
    }

    public void setLocalCount(
            int localCount) {

        this.localCount = localCount;
    }

    public int getRemoteCount() {
        return remoteCount;
    }

    public void setRemoteCount(
            int remoteCount) {

        this.remoteCount = remoteCount;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(
            int delta) {

        this.delta = delta;
    }

    public ReconciliationState getState() {
        return state;
    }

    public void setState(
            ReconciliationState state) {

        this.state = state;
    }
}