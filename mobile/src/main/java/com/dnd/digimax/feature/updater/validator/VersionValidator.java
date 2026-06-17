package com.dnd.digimax.feature.updater.validator;

public class VersionValidator {

    public boolean isUpgrade(
            String currentVersion,
            String targetVersion) {

        if (currentVersion == null
                || targetVersion == null) {

            return false;
        }

        return !currentVersion.equals(
                targetVersion
        );
    }
}