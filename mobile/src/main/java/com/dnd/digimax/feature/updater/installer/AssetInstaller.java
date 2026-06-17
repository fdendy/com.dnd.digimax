package com.dnd.digimax.feature.updater.installer;

import android.util.Log;

import com.dnd.digimax.feature.updater.model.UpdatePackage;

public class AssetInstaller {

    private static final String TAG =
            "AssetInstaller";

    public boolean install(
            UpdatePackage updatePackage) {

        Log.d(
                TAG,
                "Installing Asset Package"
        );

        return true;
    }
}