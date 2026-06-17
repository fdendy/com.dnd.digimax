package com.dnd.digimax.feature.playback.pop.audit;

import java.util.List;

public class PopAuditReporter {

    public String buildReport(
            List<PopAuditRecord> audits) {

        int valid = 0;

        int invalid = 0;

        for (PopAuditRecord record
                : audits) {

            if (record.isValid()) {

                valid++;

            } else {

                invalid++;
            }
        }

        StringBuilder builder =
                new StringBuilder();

        builder.append(
                "POP AUDIT REPORT\n"
        );

        builder.append(
                        "Valid : "
                ).append(valid)
                .append("\n");

        builder.append(
                        "Invalid : "
                ).append(invalid)
                .append("\n");

        return builder.toString();
    }
}