package com.dnd.digimax.feature.scheduler.model;

import java.util.List;

public class Schedule {

    //================================================
    // IDENTITY
    //================================================

    public String scheduleId;

    public String name;

    public int version;

    //================================================
    // RUNTIME
    //================================================

    public boolean active = true;

    //================================================
    // ZONES
    //================================================

    public List<Zone> zones;
}