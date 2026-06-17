package com.dnd.digimax.feature.updater.installer;

import android.util.Log;

import com.dnd.digimax.feature.updater.model.UpdatePackage;

public class UpdateInstaller {

    private static final String TAG =
            "UpdateInstaller";

    public boolean install(
            UpdatePackage updatePackage) {

        Log.d(
                TAG,
                "Installing version="
                        + updatePackage.getVersion()
        );

        return true;
    }
}