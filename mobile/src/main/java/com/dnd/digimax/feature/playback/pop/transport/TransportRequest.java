package com.dnd.digimax.feature.playback.pop.transport;

import java.util.HashMap;
import java.util.Map;

public class TransportRequest {

    private String url;

    private String method;

    private byte[] body;

    private final Map<String, String> headers =
            new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(
            String url) {

        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(
            String method) {

        this.method = method;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(
            byte[] body) {

        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(
            String key,
            String value) {

        headers.put(
                key,
                value
        );
    }
}