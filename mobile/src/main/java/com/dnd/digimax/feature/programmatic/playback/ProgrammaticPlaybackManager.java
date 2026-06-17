package com.dnd.digimax.feature.programmatic.playback;

import com.dnd.digimax.feature.programmatic.model.ProgrammaticDecision;

public class ProgrammaticPlaybackManager {

    public void play(ProgrammaticDecision decision) {

        if (decision == null || decision.getCampaign() == null) {
            return;
        }

        // convert → PlaylistItem → PlaybackEngine
    }
}