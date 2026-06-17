package com.dnd.digimax.feature.playback.pop.audit;

public class PopAuditRecord {

    private String contentId;

    private boolean valid;

    private String reason;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(
            String contentId) {

        this.contentId = contentId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(
            boolean valid) {

        this.valid = valid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(
            String reason) {

        this.reason = reason;
    }
}