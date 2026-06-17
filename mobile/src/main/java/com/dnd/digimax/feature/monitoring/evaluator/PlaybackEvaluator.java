package com.dnd.digimax.feature.monitoring.evaluator;

import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.policy.MonitoringPolicy;

public class PlaybackEvaluator {

    public MonitoringAlert evaluate(
            int playbackErrors,
            MonitoringPolicy policy) {

        if (playbackErrors >
                policy.getMaxPlaybackErrors()) {

            return new MonitoringAlert(
                    "playback",
                    "Playback error threshold exceeded",
                    AlertSeverity.CRITICAL
            );
        }

        return null;
    }
}