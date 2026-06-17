package com.dnd.digimax.feature.playback.pop.transport;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTransportClient
        implements TransportClient {

    private final TransportPolicy policy;

    public HttpTransportClient(
            TransportPolicy policy) {

        this.policy =
                policy;
    }

    @Override
    public TransportResponse execute(
            TransportRequest request) {

        TransportResponse response =
                new TransportResponse();

        HttpURLConnection connection =
                null;

        try {

            URL url =
                    new URL(
                            request.getUrl()
                    );

            connection =
                    (HttpURLConnection)
                            url.openConnection();

            connection.setRequestMethod(
                    request.getMethod()
            );

            connection.setConnectTimeout(
                    policy.getConnectTimeout()
            );

            connection.setReadTimeout(
                    policy.getReadTimeout()
            );

            connection.setDoOutput(
                    true
            );

            for (String key :
                    request.getHeaders().keySet()) {

                connection.setRequestProperty(
                        key,
                        request.getHeaders()
                                .get(key)
                );
            }

            if (request.getBody() != null) {

                OutputStream os =
                        connection.getOutputStream();

                os.write(
                        request.getBody()
                );

                os.flush();

                os.close();
            }

            int code =
                    connection.getResponseCode();

            response.setStatusCode(
                    code
            );

            response.setSuccess(
                    code >= 200 &&
                            code < 300
            );

        } catch (Exception e) {

            response.setSuccess(
                    false
            );

            response.setResponseBody(
                    e.getMessage()
            );
        }

        if (connection != null) {
            connection.disconnect();
        }

        return response;
    }
}