package com.dnd.digimax.feature.playback.pop.reconciliation.source;

import java.util.Map;

public interface ReconciliationSource {

    Map<String,Integer> load();
}