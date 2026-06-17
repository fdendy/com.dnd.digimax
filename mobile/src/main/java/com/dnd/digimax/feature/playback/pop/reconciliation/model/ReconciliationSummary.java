package com.dnd.digimax.feature.playback.pop.reconciliation.model;

public class ReconciliationSummary {

    private int matched;

    private int partialMatched;

    private int mismatched;

    private int missingLocal;

    private int missingRemote;

    public int getMatched() {
        return matched;
    }

    public void incrementMatched() {
        matched++;
    }

    public int getPartialMatched() {
        return partialMatched;
    }

    public void incrementPartialMatched() {
        partialMatched++;
    }

    public int getMismatched() {
        return mismatched;
    }

    public void incrementMismatched() {
        mismatched++;
    }

    public int getMissingLocal() {
        return missingLocal;
    }

    public void incrementMissingLocal() {
        missingLocal++;
    }

    public int getMissingRemote() {
        return missingRemote;
    }

    public void incrementMissingRemote() {
        missingRemote++;
    }
}