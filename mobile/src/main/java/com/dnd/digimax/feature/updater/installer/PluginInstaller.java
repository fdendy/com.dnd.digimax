package com.dnd.digimax.feature.updater.installer;

import android.util.Log;

import com.dnd.digimax.feature.updater.model.UpdatePackage;

public class PluginInstaller {

    private static final String TAG =
            "PluginInstaller";

    public boolean install(
            UpdatePackage updatePackage) {

        Log.d(
                TAG,
                "Installing Plugin Package"
        );

        return true;
    }
}