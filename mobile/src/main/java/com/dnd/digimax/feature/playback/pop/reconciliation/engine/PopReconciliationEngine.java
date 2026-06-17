package com.dnd.digimax.feature.playback.pop.reconciliation.engine;

import com.dnd.digimax.feature.playback.pop.reconciliation.model.ReconciliationResult;
import com.dnd.digimax.feature.playback.pop.reconciliation.model.ReconciliationState;
import com.dnd.digimax.feature.playback.pop.reconciliation.policy.ReconciliationPolicy;
import com.dnd.digimax.feature.playback.pop.reconciliation.source.ReconciliationSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopReconciliationEngine {

    private final ReconciliationPolicy policy;

    public PopReconciliationEngine(
            ReconciliationPolicy policy) {

        this.policy = policy;
    }

    public List<ReconciliationResult> reconcile(
            ReconciliationSource localSource,
            ReconciliationSource remoteSource) {

        Map<String,Integer> local =
                localSource.load();

        Map<String,Integer> remote =
                remoteSource.load();

        Set<String> contentIds =
                new HashSet<>();

        contentIds.addAll(
                local.keySet()
        );

        contentIds.addAll(
                remote.keySet()
        );

        List<ReconciliationResult> results =
                new ArrayList<>();

        for (String contentId : contentIds) {

            int localCount =
                    local.getOrDefault(
                            contentId,
                            -1
                    );

            int remoteCount =
                    remote.getOrDefault(
                            contentId,
                            -1
                    );

            ReconciliationResult result =
                    new ReconciliationResult();

            result.setContentId(
                    contentId
            );

            result.setLocalCount(
                    localCount
            );

            result.setRemoteCount(
                    remoteCount
            );

            int delta =
                    localCount -
                            remoteCount;

            result.setDelta(
                    delta
            );

            if (localCount < 0) {

                result.setState(
                        ReconciliationState.MISSING_LOCAL
                );

            } else if (remoteCount < 0) {

                result.setState(
                        ReconciliationState.MISSING_REMOTE
                );

            } else if (delta == 0) {

                result.setState(
                        ReconciliationState.MATCHED
                );

            } else if (
                    Math.abs(delta)
                            <= policy.getTolerance()
            ) {

                result.setState(
                        ReconciliationState.PARTIAL_MATCH
                );

            } else {

                result.setState(
                        ReconciliationState.MISMATCH
                );
            }

            results.add(
                    result
            );
        }

        return results;
    }
}