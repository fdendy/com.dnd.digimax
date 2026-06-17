package com.dnd.digimax.plugin.impl.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dnd.digimax.feature.scheduler.model.PlaylistItem;
import com.dnd.digimax.feature.scheduler.model.Plugin;
import com.dnd.digimax.plugin.base.PluginRenderer;

public class WebViewRenderer implements PluginRenderer {

    //================================================
    // VIEW
    //================================================

    private WebView webView;

    //================================================
    // DATA
    //================================================

    private Plugin plugin;

    private int playlistIndex = 0;
    private boolean pageLoaded;

    private boolean pageError;

    //================================================
    // CREATE VIEW
    //================================================

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View createView(Context context) {
        webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public void onPageStarted(
                            WebView view,
                            String url,
                            Bitmap favicon
                    ) {
                        pageLoaded = false;
                        pageError = false;
                    }

                    @Override
                    public void onPageFinished(
                            WebView view,
                            String url
                    ) {
                        pageLoaded = true;
                    }

                    @Override
                    public void onReceivedError(
                            WebView view,
                            WebResourceRequest request,
                            WebResourceError error
                    ) {
                        pageError = true;
                    }

                    @Override
                    public void onReceivedSslError(
                            WebView view,
                            SslErrorHandler handler,
                            SslError error
                    ) {
                        pageError = true;
                        handler.cancel();
                    }
                });
        return webView;
    }

    //================================================
    // INITIALIZE
    //================================================

    @Override
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
    }

    //================================================
    // PLAY
    //================================================

    @Override
    public void play() {
        loadCurrent();
    }

    //================================================
    // LOAD
    //================================================

    private void loadCurrent() {
        if (plugin == null) return;
        if (plugin.playlist == null) return;
        if (plugin.playlist.isEmpty()) return;
        if (playlistIndex >= plugin.playlist.size()) {
            playlistIndex = 0;
        }
        PlaylistItem item = plugin.playlist.get(playlistIndex);
        webView.loadUrl(item.value);
    }

    //================================================
    // PAUSE
    //================================================

    @Override
    public void pause() {
        if (webView != null) {
            webView.onPause();
        }
    }

    //================================================
    // STOP
    //================================================

    @Override
    public void stop() {
        if (webView != null) {
            webView.stopLoading();
        }
    }

    //================================================
    // RELEASE
    //================================================

    @Override
    public void release() {
        if (webView != null) {
            webView.destroy();
        }
    }
}