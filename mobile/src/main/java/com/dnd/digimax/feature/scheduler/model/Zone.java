package com.dnd.digimax.feature.scheduler.model;

public class Zone {

    public String zoneId;

    public int zIndex;

    public boolean overlay;

    public long overlayDuration;

    public long overlayInterval;

    public boolean pauseWhenOverlay;

    public Bounds bounds;

    public Plugin plugin;
    public boolean destroyed;
}