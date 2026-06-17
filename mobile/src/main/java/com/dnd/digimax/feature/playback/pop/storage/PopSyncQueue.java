package com.dnd.digimax.feature.playback.pop.storage;

import java.util.LinkedList;
import java.util.Queue;

public class PopSyncQueue {

    private final Queue<PopEntity> queue =
            new LinkedList<>();

    public void enqueue(
            PopEntity entity) {

        queue.offer(entity);
    }

    public PopEntity dequeue() {

        return queue.poll();
    }

    public boolean isEmpty() {

        return queue.isEmpty();
    }

    public int size() {

        return queue.size();
    }
}