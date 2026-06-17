package com.dnd.digimax.feature.playback.policy;

public class RetryPolicy {

    private int maxRetry = 3;

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(
            int maxRetry) {

        this.maxRetry = maxRetry;
    }
}