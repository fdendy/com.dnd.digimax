package com.dnd.digimax.feature.programmatic.pop;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;
import com.dnd.digimax.feature.programmatic.model.ProgrammaticDecision;

public class ProgrammaticPopCollector {

    public ProofOfPlayRecord create(ProgrammaticDecision decision) {

        if (decision == null || decision.getCampaign() == null) {
            return null;
        }

        ProofOfPlayRecord record = new ProofOfPlayRecord();

        record.setContentId(decision.getCampaign().getCreativeId());
        record.setProvider(decision.getProvider());

        return record;
    }
}
