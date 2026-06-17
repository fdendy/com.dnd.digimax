package com.dnd.digimax.feature.scheduler.engine;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;

import com.dnd.digimax.core.state.StateBus;
import com.dnd.digimax.core.state.StateEvent;
import com.dnd.digimax.feature.scheduler.model.Schedule;
import com.dnd.digimax.feature.scheduler.model.Zone;
import com.dnd.digimax.feature.scheduler.runtime.ZoneRuntime;
import com.dnd.digimax.plugin.base.PluginRenderer;
import com.dnd.digimax.plugin.manager.PluginOrchestrator;
import com.dnd.digimax.shared.constant.RuntimeEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScheduleEngine {
    private FrameLayout root;
    private final PluginOrchestrator orchestrator = new PluginOrchestrator();
    private final List<ZoneRuntime> runtimes = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // START ================================================

    public void start(
            Context context,
            FrameLayout root,
            Schedule schedule
    ) {
        this.root = root;
        if (schedule == null) return;
        if (schedule.zones == null) return;
        sortZones(schedule);
        for (Zone zone : schedule.zones) {
            createZone(
                    context,
                    root,
                    zone
            );
        }
    }

    // CREATE ZONE ================================================
    private void createZone(
            Context context,
            FrameLayout root,
            Zone zone
    ) {
        if (zone.plugin == null) return;

        PluginRenderer renderer = orchestrator.create(zone.plugin.type);
        if (renderer == null) return;
        renderer.initialize(zone.plugin);
        View view = renderer.createView(context);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        zone.bounds.width,
                        zone.bounds.height
                );

        view.setX(zone.bounds.x);
        view.setY(zone.bounds.y);
        root.addView(view, params);
        ZoneRuntime runtime = new ZoneRuntime();
        runtime.zone = zone;
        runtime.view = view;
        runtime.renderer = renderer;
        runtimes.add(runtime);
        if (zone.overlay) {
            startOverlayRuntime(runtime);
        } else {
            view.setVisibility(View.VISIBLE);
            renderer.play();
        }
    }

    // OVERLAY ================================================
    private void startOverlayRuntime(ZoneRuntime runtime) {
        runtime.view.setVisibility(View.GONE);
        scheduleOverlay(runtime);
    }
    private void scheduleOverlay(ZoneRuntime runtime) {
        handler.postDelayed(() -> {
            showOverlay(runtime);
        }, runtime.zone.overlayInterval);
    }
    private void showOverlay(ZoneRuntime runtime) {
        pauseBackgroundZones();
        runtime.view.setVisibility(View.VISIBLE);
        runtime.renderer.play();
        StateEvent event = new StateEvent();
        event.type = RuntimeEvents.OVERLAY_SHOW;
        event.zoneId = runtime.zone.zoneId;
        StateBus.get().emit(event);
        handler.postDelayed(() -> {
            hideOverlay(runtime);
        }, runtime.zone.overlayDuration);
    }
    private void hideOverlay(ZoneRuntime runtime) {
        runtime.view.setVisibility(View.GONE);
        runtime.renderer.pause();
        resumeBackgroundZones();
        StateEvent event = new StateEvent();
        event.type = RuntimeEvents.OVERLAY_HIDE;
        event.zoneId = runtime.zone.zoneId;
        StateBus.get().emit(event);
        scheduleOverlay(runtime);
    }

    // BACKGROUND CONTROL ================================================
    private void pauseBackgroundZones() {
        for (ZoneRuntime runtime : runtimes) {
            if (runtime.zone.overlay)
                continue;
            if (!runtime.zone.pauseWhenOverlay)
                continue;
            runtime.renderer.pause();
        }
    }
    private void resumeBackgroundZones() {
        for (ZoneRuntime runtime : runtimes) {
            if (runtime.zone.overlay)
                continue;
            if (!runtime.zone.pauseWhenOverlay)
                continue;
            runtime.renderer.play();
        }
    }

    // SORT ================================================
    private void sortZones(Schedule schedule) {
        Collections.sort(
                schedule.zones,
                new Comparator<Zone>() {
                    @Override
                    public int compare(Zone a,Zone b) {
                        return Integer.compare(
                                a.zIndex,
                                b.zIndex
                        );
                    }
                }
        );
    }

    // STOP ================================================
    public void stop() {
        for (ZoneRuntime runtime : runtimes) {
            runtime.renderer.stop();
            runtime.renderer.release();
        }
        runtimes.clear();
    }

    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        for (ZoneRuntime runtime : runtimes) {
            if (runtime.renderer != null) {
                runtime.renderer.stop();
                runtime.renderer.release();
            }

            if (runtime.view != null) {
                root.removeView(runtime.view);
            }
        }
        runtimes.clear();
    }
}