package com.dnd.digimax.feature.recovery.plan;

import com.dnd.digimax.feature.recovery.action.RecoveryAction;

public class RecoveryStep {

    private RecoveryAction action;

    private String description;

    public RecoveryAction getAction() {
        return action;
    }

    public void setAction(
            RecoveryAction action) {

        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }
}
