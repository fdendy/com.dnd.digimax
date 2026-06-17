package com.dnd.digimax.feature.recovery.engine;

import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.recovery.action.RecoveryAction;
import com.dnd.digimax.feature.recovery.executor.DeviceRecoveryExecutor;
import com.dnd.digimax.feature.recovery.executor.PlaybackRecoveryExecutor;
import com.dnd.digimax.feature.recovery.executor.RuntimeRecoveryExecutor;
import com.dnd.digimax.feature.recovery.executor.SchedulerRecoveryExecutor;
import com.dnd.digimax.feature.recovery.plan.RecoveryPlan;
import com.dnd.digimax.feature.recovery.plan.RecoveryStep;
import com.dnd.digimax.feature.recovery.policy.RecoveryPolicy;

public class RecoveryEngine {

    private final RecoveryPolicy policy;

    private final PlaybackRecoveryExecutor playbackExecutor;

    private final RuntimeRecoveryExecutor runtimeExecutor;

    private final SchedulerRecoveryExecutor schedulerExecutor;

    private final DeviceRecoveryExecutor deviceExecutor;

    public RecoveryEngine() {

        policy =
                new RecoveryPolicy();

        playbackExecutor =
                new PlaybackRecoveryExecutor();

        runtimeExecutor =
                new RuntimeRecoveryExecutor();

        schedulerExecutor =
                new SchedulerRecoveryExecutor();

        deviceExecutor =
                new DeviceRecoveryExecutor();
    }

    public RecoveryPlan createPlan(
            MonitoringAlert alert) {

        RecoveryPlan plan =
                new RecoveryPlan();

        plan.setReason(
                alert.getMessage()
        );

        RecoveryStep step =
                new RecoveryStep();

        if (alert.getSeverity()
                == AlertSeverity.WARNING) {

            step.setAction(
                    RecoveryAction.RETRY
            );

        } else {

            switch (
                    alert.getSource()) {

                case "playback":

                    step.setAction(
                            RecoveryAction.RELOAD_PLAYBACK
                    );

                    break;

                case "pop":

                    step.setAction(
                            RecoveryAction.RELOAD_SCHEDULE
                    );

                    break;

                case "telemetry":

                    step.setAction(
                            RecoveryAction.CLEAR_CACHE
                    );

                    break;

                case "runtime":

                    step.setAction(
                            RecoveryAction.RESTART_PLAYER
                    );

                    break;

                default:

                    step.setAction(
                            RecoveryAction.RETRY
                    );
            }
        }

        plan.addStep(step);

        return plan;
    }

    public boolean execute(
            RecoveryPlan plan) {

        boolean success = true;

        for (RecoveryStep step
                : plan.getSteps()) {

            success &=
                    dispatch(
                            step.getAction()
                    );
        }

        return success;
    }

    private boolean dispatch(
            RecoveryAction action) {

        if (playbackExecutor.execute(action)) {
            return true;
        }

        if (schedulerExecutor.execute(action)) {
            return true;
        }

        if (runtimeExecutor.execute(action)) {
            return true;
        }

        return deviceExecutor.execute(action);
    }

    public RecoveryPolicy getPolicy() {
        return policy;
    }
}