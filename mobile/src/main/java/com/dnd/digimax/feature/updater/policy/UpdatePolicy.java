package com.dnd.digimax.feature.updater.policy;

public class UpdatePolicy {

    private boolean allowRollback =
            true;

    private boolean allowDowngrade =
            false;

    private boolean mandatoryOnly =
            false;

    public boolean isAllowRollback() {
        return allowRollback;
    }

    public boolean isAllowDowngrade() {
        return allowDowngrade;
    }

    public boolean isMandatoryOnly() {
        return mandatoryOnly;
    }
}