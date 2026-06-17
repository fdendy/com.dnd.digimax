package com.dnd.digimax.feature.updater.model;

public class UpdatePackage {

    private String version;

    private String buildNumber;

    private String downloadUrl;

    private String checksum;

    private boolean mandatory;

    public String getVersion() {
        return version;
    }

    public void setVersion(
            String version) {

        this.version = version;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(
            String buildNumber) {

        this.buildNumber = buildNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(
            String downloadUrl) {

        this.downloadUrl = downloadUrl;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(
            String checksum) {

        this.checksum = checksum;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(
            boolean mandatory) {

        this.mandatory = mandatory;
    }
}