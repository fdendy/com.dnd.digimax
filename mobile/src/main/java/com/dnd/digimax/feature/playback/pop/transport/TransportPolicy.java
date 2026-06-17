package com.dnd.digimax.feature.playback.pop.transport;

public class TransportPolicy {

    private int connectTimeout =
            10000;

    private int readTimeout =
            10000;

    private int maxRetry =
            3;

    private long retryDelayMillis =
            3000;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(
            int connectTimeout) {

        this.connectTimeout =
                connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(
            int readTimeout) {

        this.readTimeout =
                readTimeout;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(
            int maxRetry) {

        this.maxRetry =
                maxRetry;
    }

    public long getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public void setRetryDelayMillis(
            long retryDelayMillis) {

        this.retryDelayMillis =
                retryDelayMillis;
    }
}
