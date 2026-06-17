package com.dnd.digimax.feature.playback.queue;

import java.util.LinkedList;
import java.util.Queue;

public class PlaybackQueue {

    private final Queue<PlaybackItem> queue =
            new LinkedList<>();

    public void enqueue(
            PlaybackItem item) {

        queue.offer(item);
    }

    public PlaybackItem dequeue() {

        return queue.poll();
    }

    public PlaybackItem peek() {

        return queue.peek();
    }

    public boolean isEmpty() {

        return queue.isEmpty();
    }

    public int size() {

        return queue.size();
    }

    public void clear() {

        queue.clear();
    }
}