package com.dnd.digimax.feature.updater.rollback;

public class RollbackManager {

    private RollbackSnapshot snapshot;

    public void save(
            String version) {

        snapshot =
                new RollbackSnapshot();

        snapshot.setVersion(
                version
        );

        snapshot.setTimestamp(
                System.currentTimeMillis()
        );
    }

    public RollbackSnapshot getSnapshot() {

        return snapshot;
    }

    public boolean rollback() {

        return snapshot != null;
    }
}