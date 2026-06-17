package com.dnd.digimax.feature.updater.model;

import java.util.ArrayList;
import java.util.List;

public class UpdateManifest {

    private String manifestVersion;

    private UpdateChannel channel;

    private final List<UpdatePackage>
            packages =
            new ArrayList<>();

    public String getManifestVersion() {
        return manifestVersion;
    }

    public void setManifestVersion(
            String manifestVersion) {

        this.manifestVersion =
                manifestVersion;
    }

    public UpdateChannel getChannel() {
        return channel;
    }

    public void setChannel(
            UpdateChannel channel) {

        this.channel =
                channel;
    }

    public List<UpdatePackage> getPackages() {
        return packages;
    }

    public void addPackage(
            UpdatePackage updatePackage) {

        packages.add(
                updatePackage
        );
    }
}