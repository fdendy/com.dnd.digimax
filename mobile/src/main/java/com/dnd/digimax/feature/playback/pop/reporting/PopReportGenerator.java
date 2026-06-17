package com.dnd.digimax.feature.playback.pop.reporting;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopReportGenerator {

    public PopReport generate(
            List<ProofOfPlayRecord> records) {

        Map<String, PopSummary> summaryMap =
                new HashMap<>();

        long totalDuration = 0;

        for (ProofOfPlayRecord record : records) {

            PopSummary summary =
                    summaryMap.get(
                            record.getContentId()
                    );

            if (summary == null) {

                summary =
                        new PopSummary();

                summary.setContentId(
                        record.getContentId()
                );

                summaryMap.put(
                        record.getContentId(),
                        summary
                );
            }

            summary.addImpression();

            summary.addDuration(
                    record.getDuration()
            );

            totalDuration +=
                    record.getDuration();
        }

        List<PopSummary> summaries =
                new ArrayList<>(
                        summaryMap.values()
                );

        PopAnalytics analytics =
                new PopAnalytics();

        analytics.setTotalImpressions(
                records.size()
        );

        analytics.setTotalPlaybackDuration(
                totalDuration
        );

        analytics.setUniqueContents(
                summaries.size()
        );

        PopReport report =
                new PopReport();

        report.setGeneratedAt(
                System.currentTimeMillis()
        );

        report.setAnalytics(
                analytics
        );

        report.setSummaries(
                summaries
        );

        return report;
    }
}