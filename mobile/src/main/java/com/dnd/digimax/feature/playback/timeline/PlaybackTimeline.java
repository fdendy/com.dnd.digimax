package com.dnd.digimax.feature.playback.timeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaybackTimeline {

    private final List<PlaybackMarker> markers =
            new ArrayList<>();

    public void add(
            PlaybackMarker marker) {

        markers.add(marker);
    }

    public List<PlaybackMarker> getMarkers() {

        return Collections.unmodifiableList(markers);
    }

    public void clear() {

        markers.clear();
    }
}