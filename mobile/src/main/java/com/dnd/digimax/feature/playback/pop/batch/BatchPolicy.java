package com.dnd.digimax.feature.playback.pop.batch;

public class BatchPolicy {

    private int maxRecords;

    private long maxWaitMillis;

    public BatchPolicy() {

        maxRecords = 100;

        maxWaitMillis = 60000;
    }

    public int getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(
            int maxRecords) {

        this.maxRecords = maxRecords;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(
            long maxWaitMillis) {

        this.maxWaitMillis = maxWaitMillis;
    }
}