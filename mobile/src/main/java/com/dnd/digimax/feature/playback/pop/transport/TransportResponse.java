package com.dnd.digimax.feature.playback.pop.transport;

public class TransportResponse {

    private int statusCode;

    private String responseBody;

    private boolean success;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(
            int statusCode) {

        this.statusCode = statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(
            String responseBody) {

        this.responseBody = responseBody;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(
            boolean success) {

        this.success = success;
    }
}