package com.dnd.digimax.feature.playback.pop.audit;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;

public class PopAuditValidator {

    public PopAuditRecord validate(
            ProofOfPlayRecord record) {

        PopAuditRecord result =
                new PopAuditRecord();

        result.setContentId(
                record.getContentId()
        );

        if (record.getContentId() == null) {

            result.setValid(false);

            result.setReason(
                    "Missing contentId"
            );

            return result;
        }

        if (record.getDuration() <= 0) {

            result.setValid(false);

            result.setReason(
                    "Invalid duration"
            );

            return result;
        }

        if (!record.isCompleted()) {

            result.setValid(false);

            result.setReason(
                    "Playback not completed"
            );

            return result;
        }

        result.setValid(true);

        result.setReason("OK");

        return result;
    }
}