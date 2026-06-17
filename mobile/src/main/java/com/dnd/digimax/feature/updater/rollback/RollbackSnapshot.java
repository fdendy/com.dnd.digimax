package com.dnd.digimax.feature.updater.rollback;

public class RollbackSnapshot {

    private String version;

    private long timestamp;

    public String getVersion() {
        return version;
    }

    public void setVersion(
            String version) {

        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(
            long timestamp) {

        this.timestamp = timestamp;
    }
}
