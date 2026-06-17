package com.dnd.digimax.feature.playback.pop.transport;

public interface TransportClient {

    TransportResponse execute(
            TransportRequest request
    );
}