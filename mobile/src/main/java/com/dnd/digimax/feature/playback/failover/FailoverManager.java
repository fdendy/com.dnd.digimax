package com.dnd.digimax.feature.playback.failover;

import com.dnd.digimax.feature.playback.queue.PlaybackItem;

public class FailoverManager {

    private PlaybackItem fallbackItem;

    public void setFallbackItem(
            PlaybackItem fallbackItem) {

        this.fallbackItem = fallbackItem;
    }

    public PlaybackItem getFallbackItem() {

        return fallbackItem;
    }
}