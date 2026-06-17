package com.dnd.digimax.core.app;

import android.app.Application;

public class DigimaxApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.init(this);
        AppController.get().initialize(null);
    }
}