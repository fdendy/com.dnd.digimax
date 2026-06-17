package com.dnd.digimax.feature.playback.engine;

import com.dnd.digimax.feature.playback.failover.FailoverManager;
import com.dnd.digimax.feature.playback.metrics.PlaybackMetrics;
import com.dnd.digimax.feature.playback.policy.PlaybackPolicy;
import com.dnd.digimax.feature.playback.recovery.PlaybackRecoveryManager;
import com.dnd.digimax.feature.playback.recovery.RecoveryAction;
import com.dnd.digimax.feature.playback.session.PlaybackContext;
import com.dnd.digimax.feature.playback.session.PlaybackSession;
import com.dnd.digimax.feature.playback.state.PlaybackState;
import com.dnd.digimax.feature.playback.state.PlaybackStateMachine;

import java.util.UUID;

public class PlaybackEngine {

    // STATE =========================================================

    private final PlaybackStateMachine stateMachine;

    // SESSION =========================================================

    private PlaybackSession session;

    private final PlaybackContext context;

    // METRICS =========================================================

    private final PlaybackMetrics metrics;

    // POLICY =========================================================

    private final PlaybackPolicy policy;

    // RECOVERY =========================================================

    private final PlaybackRecoveryManager recoveryManager;

    // FAILOVER =========================================================

    private final FailoverManager failoverManager;

    // CONSTRUCTOR =========================================================

    public PlaybackEngine() {

        stateMachine =
                new PlaybackStateMachine();

        context =
                new PlaybackContext();

        metrics =
                new PlaybackMetrics();

        policy =
                new PlaybackPolicy();

        recoveryManager =
                new PlaybackRecoveryManager();

        failoverManager =
                new FailoverManager();
    }

    // SESSION =========================================================

    public synchronized void startSession() {

        session =
                new PlaybackSession(
                        UUID.randomUUID().toString()
                );

        context.setRetryCount(0);
        context.setRecoveryMode(false);

        transition(PlaybackState.IDLE);
    }

    public synchronized void stopSession() {

        transition(PlaybackState.COMPLETED);
    }

    // PLAYBACK =========================================================

    public synchronized void startPlayback() {

        context.setContentStartedAt(
                System.currentTimeMillis()
        );

        transition(PlaybackState.PLAYING);
    }

    public synchronized void pausePlayback() {

        transition(PlaybackState.PAUSED);
    }

    public synchronized void resumePlayback() {

        transition(PlaybackState.PLAYING);
    }

    public synchronized void completePlayback() {

        context.setContentEndedAt(
                System.currentTimeMillis()
        );

        long duration =
                context.getContentEndedAt()
                        - context.getContentStartedAt();

        metrics.addPlayTime(duration);

        metrics.incrementCompletedContent();

        context.setRetryCount(0);
        context.setRecoveryMode(false);

        transition(PlaybackState.COMPLETED);
    }

    // ERROR =========================================================

    public synchronized void onPlaybackError() {

        metrics.incrementPlaybackErrors();

        context.setRecoveryMode(true);

        RecoveryAction action =
                recoveryManager.resolve(
                        context.getRetryCount(),
                        policy.getRetryPolicy()
                                .getMaxRetry()
                );

        switch (action) {

            case RETRY:

                context.setRetryCount(
                        context.getRetryCount() + 1
                );

                transition(PlaybackState.LOADING);

                break;

            case SKIP:

                transition(PlaybackState.ERROR);

                break;

            case FAILOVER:

                transition(PlaybackState.ERROR);

                break;

            default:

                transition(PlaybackState.ERROR);

                break;
        }
    }

    // FAILOVER =========================================================

    public synchronized boolean hasFailoverContent() {

        return failoverManager.getFallbackItem()
                != null;
    }

    // STATE =========================================================

    private void transition(
            PlaybackState targetState) {

        stateMachine.transition(
                targetState
        );

        context.setState(
                targetState
        );
    }

    // GETTER =========================================================

    public PlaybackSession getSession() {
        return session;
    }

    public PlaybackContext getContext() {
        return context;
    }

    public PlaybackMetrics getMetrics() {
        return metrics;
    }

    public PlaybackPolicy getPolicy() {
        return policy;
    }

    public PlaybackRecoveryManager getRecoveryManager() {
        return recoveryManager;
    }

    public FailoverManager getFailoverManager() {
        return failoverManager;
    }

    public PlaybackState getState() {
        return stateMachine.getState();
    }

    // UTILITY =========================================================

    public boolean isPlaying() {

        return getState() ==
                PlaybackState.PLAYING;
    }

    public boolean isPaused() {

        return getState() ==
                PlaybackState.PAUSED;
    }

    public boolean isIdle() {

        return getState() ==
                PlaybackState.IDLE;
    }

    public boolean isFailed() {

        return getState() ==
                PlaybackState.ERROR;
    }
}