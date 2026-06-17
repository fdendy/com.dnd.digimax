package com.dnd.digimax.feature.orchestrator.system;

import com.dnd.digimax.feature.orchestrator.monitoring.MonitoringOrchestrator;
import com.dnd.digimax.feature.orchestrator.playback.PlaybackOrchestrator;
import com.dnd.digimax.feature.orchestrator.runtime.RuntimeOrchestrator;
import com.dnd.digimax.feature.orchestrator.startup.StartupOrchestrator;

public class SystemOrchestrator {

    private final RuntimeOrchestrator runtime;

    private final StartupOrchestrator startup;

    private final PlaybackOrchestrator playback;

    private final MonitoringOrchestrator monitoring;

    public SystemOrchestrator(
            RuntimeOrchestrator runtime,
            StartupOrchestrator startup,
            PlaybackOrchestrator playback,
            MonitoringOrchestrator monitoring) {

        this.runtime =
                runtime;

        this.startup =
                startup;

        this.playback =
                playback;

        this.monitoring =
                monitoring;
    }

    public boolean startup() {

        runtime.initialize();

        boolean success =
                startup.startup();

        if (!success) {

            runtime.recovery();

            return false;
        }

        runtime.ready();

        runtime.running();

        return true;
    }

    public void shutdown() {

        playback.stop();

        runtime.stop();
    }

    public PlaybackOrchestrator playback() {

        return playback;
    }

    public MonitoringOrchestrator monitoring() {

        return monitoring;
    }
}