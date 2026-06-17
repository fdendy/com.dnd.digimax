package com.dnd.digimax.feature.scheduler.parser;

import android.content.Context;

import com.dnd.digimax.R;
import com.dnd.digimax.feature.scheduler.model.Bounds;
import com.dnd.digimax.feature.scheduler.model.PlaylistItem;
import com.dnd.digimax.feature.scheduler.model.Plugin;
import com.dnd.digimax.feature.scheduler.model.Schedule;
import com.dnd.digimax.feature.scheduler.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class ScheduleParser {
    public Schedule loadDummy(Context context) {
        List<Zone> zones = new ArrayList<>();
        zones.add(createVideoZone(context));
        zones.add(createTextZone());
        zones.add(createWebviewZone());
        Schedule schedule = new Schedule();
        schedule.zones = zones;
        return schedule;
    }
    public Schedule loadAlternative(Context context) {
        List<Zone> zones = new ArrayList<>();
        zones.add(createImageZone(context));
        zones.add(createAlternativeTextZone());
        zones.add(createAlternativeWebviewZone());
        Schedule schedule = new Schedule();
        schedule.scheduleId = "ALT-SCHEDULE";
        schedule.name = "Alternative Schedule";
        schedule.zones = zones;
        return schedule;
    }
    private Zone createVideoZone(Context context) {
        PlaylistItem item = new PlaylistItem();
        item.type = "video";
        item.value = "android.resource://" +
                        context.getPackageName() +
                        "/" +
                        R.raw.sample;

        PlaylistItem item2 = new PlaylistItem();
        item2.type = "image";
        item2.value = "android.resource://" +
                context.getPackageName() +
                "/" +
                R.drawable.amazon;
        item2.duration = 10000;


        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(item);
        playlist.add(item2);

        Plugin plugin = new Plugin();
        plugin.type = "exoplayer";
        plugin.playlist = playlist;

        Bounds bounds = new Bounds();
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 1920;
        bounds.height = 1080;

        Zone zone = new Zone();
        zone.zoneId = "ZONE-VIDEO";
        zone.bounds = bounds;
        zone.plugin = plugin;
        zone.zIndex = 1;
        zone.pauseWhenOverlay = true;
        return zone;
    }
    private Zone createTextZone() {
        PlaylistItem item = new PlaylistItem();
        item.type = "text";
        item.value = "SELAMAT DATANG DI DIGIMAX SIGNAGE ENGINE";

        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(item);

        Plugin plugin = new Plugin();
        plugin.type = "runningtext";
        plugin.playlist = playlist;

        Bounds bounds = new Bounds();
        bounds.x = 0;
        bounds.y = 1000;
        bounds.width = 1920;
        bounds.height = 80;

        Zone zone = new Zone();
        zone.zoneId = "ZONE-TEXT";
        zone.bounds = bounds;
        zone.plugin = plugin;
        zone.zIndex = 2;
        zone.pauseWhenOverlay = false;
        return zone;
    }
    private Zone createWebviewZone() {
        PlaylistItem item = new PlaylistItem();
        item.type = "url";
        item.value = "https://www.google.com";

        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(item);

        Plugin plugin = new Plugin();
        plugin.type = "webview";
        plugin.playlist = playlist;

        Bounds bounds = new Bounds();
        bounds.x = 200;
        bounds.y = 120;
        bounds.width = 1520;
        bounds.height = 760;

        Zone zone = new Zone();
        zone.zoneId = "ZONE-WEB";
        zone.bounds = bounds;
        zone.plugin = plugin;
        zone.zIndex = 100;
        zone.overlay = true;
        zone.overlayDuration = 10000;
        zone.overlayInterval = 30000;
        return zone;
    }
    private Zone createImageZone(Context context) {
        PlaylistItem image1 = new PlaylistItem();
        image1.type = "image";
        image1.value = "android.resource://" +
                        context.getPackageName() +
                        "/" +
                        R.drawable.amazon;
        image1.duration = 5000;
        PlaylistItem image2 = new PlaylistItem();
        image2.type = "image";
        image2.value ="android.resource://" +
                        context.getPackageName() +
                        "/" +
                        R.drawable.amazon;

        image2.duration = 5000;

        List<PlaylistItem> playlist =
                new ArrayList<>();

        playlist.add(image1);

        playlist.add(image2);

        Plugin plugin =
                new Plugin();

        plugin.type = "exoplayer";

        plugin.playlist = playlist;

        Bounds bounds =
                new Bounds();

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 1920;
        bounds.height = 1080;

        Zone zone =
                new Zone();

        zone.zoneId = "ZONE-IMAGE";

        zone.bounds = bounds;

        zone.plugin = plugin;

        zone.zIndex = 1;

        return zone;
    }
    private Zone createAlternativeTextZone() {
        PlaylistItem item = new PlaylistItem();
        item.type = "text";
        item.value = "HOT RELOAD SUCCESS";
        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(item);
        Plugin plugin = new Plugin();
        plugin.type = "runningtext";
        plugin.playlist = playlist;
        Bounds bounds = new Bounds();

        bounds.x = 0;
        bounds.y = 950;
        bounds.width = 1920;
        bounds.height = 100;

        Zone zone = new Zone();
        zone.zoneId = "ZONE-TEXT-ALT";
        zone.bounds = bounds;
        zone.plugin = plugin;
        zone.zIndex = 10;
        return zone;
    }
    private Zone createAlternativeWebviewZone() {
        PlaylistItem item = new PlaylistItem();
        item.type = "url";
        item.value = "https://developer.android.com";
        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(item);
        Plugin plugin = new Plugin();
        plugin.type = "webview";
        plugin.playlist = playlist;
        Bounds bounds = new Bounds();
        bounds.x = 300;
        bounds.y = 150;
        bounds.width = 1300;
        bounds.height = 700;
        Zone zone = new Zone();
        zone.zoneId = "ZONE-WEB-ALT";
        zone.bounds = bounds;
        zone.plugin = plugin;
        zone.zIndex = 100;
        zone.overlay = true;
        zone.overlayDuration = 15000;
        zone.overlayInterval = 20000;
        return zone;
    }
}