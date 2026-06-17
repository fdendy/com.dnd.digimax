package com.dnd.digimax.feature.playback.pop.audit;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;

import java.util.ArrayList;
import java.util.List;

public class PopAuditEngine {

    private final PopAuditValidator validator;

    public PopAuditEngine() {

        validator =
                new PopAuditValidator();
    }

    public List<PopAuditRecord> audit(
            List<ProofOfPlayRecord> records) {

        List<PopAuditRecord> results =
                new ArrayList<>();

        for (ProofOfPlayRecord record
                : records) {

            results.add(
                    validator.validate(
                            record
                    )
            );
        }

        return results;
    }
}