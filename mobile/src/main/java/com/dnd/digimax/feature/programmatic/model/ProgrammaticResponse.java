package com.dnd.digimax.feature.programmatic.model;

public class ProgrammaticResponse {

    private boolean success;
    private String provider;
    private String message;
    private ProgrammaticCampaign campaign;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProgrammaticCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(ProgrammaticCampaign campaign) {
        this.campaign = campaign;
    }
}