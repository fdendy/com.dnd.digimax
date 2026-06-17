package com.dnd.digimax.feature.updater.validator;

import com.dnd.digimax.feature.updater.model.UpdatePackage;

public class UpdateValidator {

    public boolean validate(
            UpdatePackage updatePackage) {

        return updatePackage != null
                && updatePackage.getVersion() != null
                && updatePackage.getChecksum() != null
                && updatePackage.getDownloadUrl() != null;
    }
}