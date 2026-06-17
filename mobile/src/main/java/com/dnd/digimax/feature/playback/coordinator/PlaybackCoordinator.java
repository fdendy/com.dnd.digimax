package com.dnd.digimax.feature.playback.coordinator;

import com.dnd.digimax.feature.playback.queue.PlaybackItem;
import com.dnd.digimax.feature.playback.queue.PlaybackQueue;
import com.dnd.digimax.feature.playback.timeline.PlaybackMarker;
import com.dnd.digimax.feature.playback.timeline.PlaybackTimeline;

public class PlaybackCoordinator {

    private final PlaybackQueue queue;

    private final PlaybackTimeline timeline;

    public PlaybackCoordinator() {

        queue = new PlaybackQueue();
        timeline = new PlaybackTimeline();
    }

    public void enqueue(
            PlaybackItem item) {

        queue.enqueue(item);
    }

    public PlaybackItem next() {

        PlaybackItem item =
                queue.dequeue();

        if (item != null) {

            timeline.add(
                    new PlaybackMarker(
                            item.getContentId()
                    )
            );
        }

        return item;
    }

    public PlaybackQueue getQueue() {

        return queue;
    }

    public PlaybackTimeline getTimeline() {

        return timeline;
    }
}