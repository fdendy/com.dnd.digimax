package com.dnd.digimax.feature.programmatic.telemetry;

public class ProgrammaticTelemetry {

    private int requests;
    private int success;
    private int failed;
    private int impressions;

    public void recordRequest() { requests++; }
    public void recordSuccess() { success++; }
    public void recordFailed() { failed++; }
    public void recordImpression() { impressions++; }
}