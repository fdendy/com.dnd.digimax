package com.dnd.digimax.feature.playback.recovery;

public class PlaybackRecoveryManager {

    public RecoveryAction resolve(
            int retryCount,
            int maxRetry) {

        if (retryCount < maxRetry) {

            return RecoveryAction.RETRY;
        }

        return RecoveryAction.SKIP;
    }
}