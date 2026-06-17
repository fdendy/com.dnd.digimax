package com.dnd.digimax.feature.playback.pop.storage;

import java.util.List;

public class PopSyncManager {

    private final PopRepository repository;

    private final PopSyncQueue queue;

    public PopSyncManager(
            PopRepository repository) {

        this.repository =
                repository;

        this.queue =
                new PopSyncQueue();
    }

    public void loadPending() {

        List<PopEntity> records =
                repository.getPending();

        for (PopEntity entity : records) {

            queue.enqueue(
                    entity
            );
        }
    }

    public void sync() {

        while (!queue.isEmpty()) {

            PopEntity entity =
                    queue.dequeue();

            boolean success =
                    upload(entity);

            if (success) {

                repository.markUploaded(
                        entity.id
                );
            }
        }
    }

    private boolean upload(
            PopEntity entity) {

        return true;
    }
}