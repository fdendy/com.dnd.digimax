package com.dnd.digimax.plugin.manager;

import com.dnd.digimax.plugin.base.PluginRenderer;
import com.dnd.digimax.plugin.impl.exoplayer.ExoPlayerRenderer;
import com.dnd.digimax.plugin.impl.runningtext.RunningTextRenderer;
import com.dnd.digimax.plugin.impl.webview.WebViewRenderer;

public class PluginOrchestrator {

    public PluginRenderer create(String type) {

        if ("exoplayer".equals(type)) {
            return new ExoPlayerRenderer();
        }

        if ("runningtext".equals(type)) {
            return new RunningTextRenderer();
        }

        if ("webview".equals(type)) {
            return new WebViewRenderer();
        }

        return null;
    }
}