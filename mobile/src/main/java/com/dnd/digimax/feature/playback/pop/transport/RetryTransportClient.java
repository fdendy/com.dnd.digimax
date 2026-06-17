package com.dnd.digimax.feature.playback.pop.transport;

public class RetryTransportClient
        implements TransportClient {

    private final TransportClient client;

    private final TransportPolicy policy;

    public RetryTransportClient(
            TransportClient client,
            TransportPolicy policy) {

        this.client =
                client;

        this.policy =
                policy;
    }

    @Override
    public TransportResponse execute(
            TransportRequest request) {

        TransportResponse response =
                null;

        for (int i = 0;
             i < policy.getMaxRetry();
             i++) {

            response =
                    client.execute(
                            request
                    );

            if (response.isSuccess()) {

                return response;
            }

            try {

                Thread.sleep(
                        policy.getRetryDelayMillis()
                );

            } catch (Exception ignored) {
            }
        }

        return response;
    }
}