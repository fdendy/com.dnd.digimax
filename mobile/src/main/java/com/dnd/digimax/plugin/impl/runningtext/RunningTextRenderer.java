package com.dnd.digimax.plugin.impl.runningtext;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dnd.digimax.feature.scheduler.model.PlaylistItem;
import com.dnd.digimax.feature.scheduler.model.Plugin;
import com.dnd.digimax.plugin.base.PluginRenderer;

public class RunningTextRenderer implements PluginRenderer {
    private FrameLayout container;
    private TextView textView;
    private Plugin plugin;

    @Override
    public View createView(Context context) {
        container = new FrameLayout(context);
        textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLACK);
        textView.setTextSize(24);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setSingleLine(true);
        textView.setPadding(30, 0, 30, 0);
        container.addView(
                textView,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );
        return container;
    }

    @Override
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void play() {
        if (plugin == null) return;
        if (plugin.playlist == null) return;
        if (plugin.playlist.isEmpty()) return;

        PlaylistItem item = plugin.playlist.get(0);
        textView.setText(item.value);
        startMarquee();
    }

    private void startMarquee() {
        container.post(() -> {
            float startX = container.getWidth();
            float endX = -textView.getWidth();
            textView.setTranslationX(startX);
            ObjectAnimator animator =
                    ObjectAnimator.ofFloat(
                            textView,
                            "translationX",
                            startX,
                            endX
                    );
            animator.setDuration(15000);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.start();
        });
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }
}