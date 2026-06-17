package com.dnd.digimax.feature.monitoring.engine;

import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.evaluator.PlaybackEvaluator;
import com.dnd.digimax.feature.monitoring.evaluator.PopEvaluator;
import com.dnd.digimax.feature.monitoring.evaluator.RuntimeEvaluator;
import com.dnd.digimax.feature.monitoring.evaluator.TelemetryEvaluator;
import com.dnd.digimax.feature.monitoring.policy.MonitoringPolicy;
import com.dnd.digimax.feature.monitoring.reporter.MonitoringReporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonitoringEngine {

    private final MonitoringPolicy policy;

    private final MonitoringReporter reporter;

    private final TelemetryEvaluator telemetryEvaluator;

    private final PlaybackEvaluator playbackEvaluator;

    private final PopEvaluator popEvaluator;

    private final RuntimeEvaluator runtimeEvaluator;

    private final List<MonitoringAlert>
            alerts =
            new ArrayList<>();

    public MonitoringEngine() {

        policy =
                new MonitoringPolicy();

        reporter =
                new MonitoringReporter();

        telemetryEvaluator =
                new TelemetryEvaluator();

        playbackEvaluator =
                new PlaybackEvaluator();

        popEvaluator =
                new PopEvaluator();

        runtimeEvaluator =
                new RuntimeEvaluator();
    }

    public synchronized void evaluateTelemetry(
            int queueSize) {

        addAlert(
                telemetryEvaluator.evaluate(
                        queueSize,
                        policy
                )
        );
    }

    public synchronized void evaluatePlayback(
            int playbackErrors) {

        addAlert(
                playbackEvaluator.evaluate(
                        playbackErrors,
                        policy
                )
        );
    }

    public synchronized void evaluatePop(
            int pendingPop) {

        addAlert(
                popEvaluator.evaluate(
                        pendingPop,
                        policy
                )
        );
    }

    public synchronized void evaluateRuntime(
            int runtimeRisk) {

        addAlert(
                runtimeEvaluator.evaluate(
                        runtimeRisk,
                        policy
                )
        );
    }

    private void addAlert(
            MonitoringAlert alert) {

        if (alert == null) {
            return;
        }

        alerts.add(
                alert
        );
    }

    public List<MonitoringAlert> getAlerts() {

        return Collections.unmodifiableList(
                alerts
        );
    }

    public boolean hasCriticalAlert() {

        for (MonitoringAlert alert
                : alerts) {

            if (alert.getSeverity()
                    == AlertSeverity.CRITICAL) {

                return true;
            }
        }

        return false;
    }

    public String generateReport() {

        return reporter.buildReport(
                alerts
        );
    }

    public void clear() {

        alerts.clear();
    }
}