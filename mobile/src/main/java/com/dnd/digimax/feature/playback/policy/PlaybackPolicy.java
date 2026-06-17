package com.dnd.digimax.feature.playback.policy;

public class PlaybackPolicy {

    private final RetryPolicy retryPolicy;

    private final FailoverPolicy failoverPolicy;

    public PlaybackPolicy() {

        retryPolicy =
                new RetryPolicy();

        failoverPolicy =
                new FailoverPolicy();
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public FailoverPolicy getFailoverPolicy() {
        return failoverPolicy;
    }
}