package com.dnd.digimax.feature.playback.session;

import com.dnd.digimax.feature.playback.state.PlaybackState;

public class PlaybackContext {

    private PlaybackState state;

    private String scheduleId;

    private String playlistId;

    private String contentId;

    private String pluginType;

    private int contentIndex;

    private int retryCount;

    private long contentStartedAt;

    private long contentEndedAt;

    private boolean recoveryMode;

    public PlaybackContext() {

        state = PlaybackState.IDLE;
    }

    // STATE =========================================================

    public PlaybackState getState() {
        return state;
    }

    public void setState(
            PlaybackState state) {

        this.state = state;
    }

    // SCHEDULE =========================================================

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

    // CONTENT =========================================================

    public String getContentId() {
        return contentId;
    }

    public void setContentId(
            String contentId) {

        this.contentId = contentId;
    }

    public int getContentIndex() {
        return contentIndex;
    }

    public void setContentIndex(
            int contentIndex) {

        this.contentIndex = contentIndex;
    }

    // PLUGIN =========================================================

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(
            String pluginType) {

        this.pluginType = pluginType;
    }

    // RETRY =========================================================

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(
            int retryCount) {

        this.retryCount = retryCount;
    }

    // TIMESTAMP =========================================================

    public long getContentStartedAt() {
        return contentStartedAt;
    }

    public void setContentStartedAt(
            long contentStartedAt) {

        this.contentStartedAt = contentStartedAt;
    }

    public long getContentEndedAt() {
        return contentEndedAt;
    }

    public void setContentEndedAt(
            long contentEndedAt) {

        this.contentEndedAt = contentEndedAt;
    }

    // RECOVERY =========================================================

    public boolean isRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(
            boolean recoveryMode) {

        this.recoveryMode = recoveryMode;
    }
}