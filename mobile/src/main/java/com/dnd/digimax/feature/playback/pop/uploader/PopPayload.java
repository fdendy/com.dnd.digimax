package com.dnd.digimax.feature.playback.pop.uploader;

public class PopPayload {

    private String deviceId;

    private String sessionId;

    private String scheduleId;

    private String playlistId;

    private String contentId;

    private String pluginType;

    private long startedAt;

    private long completedAt;

    private long duration;

    private boolean completed;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(
            String deviceId) {

        this.deviceId = deviceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(
            String sessionId) {

        this.sessionId = sessionId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(
            String scheduleId) {

        this.scheduleId = scheduleId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(
            String playlistId) {

        this.playlistId = playlistId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(
            String contentId) {

        this.contentId = contentId;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(
            String pluginType) {

        this.pluginType = pluginType;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(
            long startedAt) {

        this.startedAt = startedAt;
    }

    public long getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(
            long completedAt) {

        this.completedAt = completedAt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(
            long duration) {

        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(
            boolean completed) {

        this.completed = completed;
    }
}
