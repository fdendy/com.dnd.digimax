package com.dnd.digimax.feature.playback.pop.reconciliation.policy;

public class ReconciliationPolicy {

    private int tolerance =
            5;

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(
            int tolerance) {

        this.tolerance =
                tolerance;
    }
}