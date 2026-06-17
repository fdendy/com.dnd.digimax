package com.dnd.digimax.feature.playback.pop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProofOfPlayCollector {

    private final List<ProofOfPlayRecord> records =
            new ArrayList<>();

    public synchronized void collect(
            ProofOfPlayRecord record) {

        records.add(record);
    }

    public synchronized List<ProofOfPlayRecord> getRecords() {

        return Collections.unmodifiableList(
                records
        );
    }

    public synchronized void clear() {

        records.clear();
    }

    public synchronized int size() {

        return records.size();
    }
}