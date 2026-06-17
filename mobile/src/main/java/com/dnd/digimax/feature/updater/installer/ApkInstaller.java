package com.dnd.digimax.feature.updater.installer;

import android.util.Log;

import com.dnd.digimax.feature.updater.model.UpdatePackage;

public class ApkInstaller {

    private static final String TAG =
            "ApkInstaller";

    public boolean install(
            UpdatePackage updatePackage) {

        Log.d(
                TAG,
                "Installing APK "
                        + updatePackage.getVersion()
        );

        return true;
    }
}