package com.dnd.digimax.feature.updater.model;

public class UpdateResult {

    private boolean success;

    private String version;

    private String message;

    public UpdateResult(
            boolean success,
            String version,
            String message) {

        this.success =
                success;

        this.version =
                version;

        this.message =
                message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getVersion() {
        return version;
    }

    public String getMessage() {
        return message;
    }
}