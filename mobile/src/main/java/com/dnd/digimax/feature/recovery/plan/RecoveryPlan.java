package com.dnd.digimax.feature.recovery.plan;

import java.util.ArrayList;
import java.util.List;

public class RecoveryPlan {

    private final List<RecoveryStep>
            steps =
            new ArrayList<>();

    private String reason;

    public void addStep(
            RecoveryStep step) {

        steps.add(step);
    }

    public List<RecoveryStep> getSteps() {

        return steps;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(
            String reason) {

        this.reason = reason;
    }
}