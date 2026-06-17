package com.dnd.digimax.feature.playback.pop.batch;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;

import java.util.ArrayList;
import java.util.List;

public class BatchBuilder {

    private final List<ProofOfPlayRecord>
            records =
            new ArrayList<>();

    public void add(
            ProofOfPlayRecord record) {

        records.add(record);
    }

    public List<ProofOfPlayRecord> build() {

        return new ArrayList<>(records);
    }

    public void clear() {

        records.clear();
    }

    public int size() {

        return records.size();
    }

    public boolean isEmpty() {

        return records.isEmpty();
    }
}