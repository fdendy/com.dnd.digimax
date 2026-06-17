package com.dnd.digimax.plugin.base;

import android.content.Context;
import android.view.View;

import com.dnd.digimax.feature.scheduler.model.Plugin;

public interface PluginRenderer {

    View createView(Context context);

    void initialize(Plugin plugin);

    void play();

    void pause();

    void stop();

    void release();
}