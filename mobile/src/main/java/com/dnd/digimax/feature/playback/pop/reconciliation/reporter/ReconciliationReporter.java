package com.dnd.digimax.feature.playback.pop.reconciliation.reporter;

import com.dnd.digimax.feature.playback.pop.reconciliation.model.ReconciliationResult;
import com.dnd.digimax.feature.playback.pop.reconciliation.model.ReconciliationSummary;
import com.dnd.digimax.feature.playback.pop.reconciliation.model.ReconciliationState;

import java.util.List;

public class ReconciliationReporter {

    public ReconciliationSummary summarize(
            List<ReconciliationResult> results) {

        ReconciliationSummary summary =
                new ReconciliationSummary();

        for (ReconciliationResult result
                : results) {

            switch (
                    result.getState()) {

                case MATCHED:
                    summary.incrementMatched();
                    break;

                case PARTIAL_MATCH:
                    summary.incrementPartialMatched();
                    break;

                case MISMATCH:
                    summary.incrementMismatched();
                    break;

                case MISSING_LOCAL:
                    summary.incrementMissingLocal();
                    break;

                case MISSING_REMOTE:
                    summary.incrementMissingRemote();
                    break;
            }
        }

        return summary;
    }
}