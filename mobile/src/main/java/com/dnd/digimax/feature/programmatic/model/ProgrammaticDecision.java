package com.dnd.digimax.feature.programmatic.model;

public class ProgrammaticDecision {

    private String provider;
    private ProgrammaticCampaign campaign;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ProgrammaticCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(ProgrammaticCampaign campaign) {
        this.campaign = campaign;
    }
}