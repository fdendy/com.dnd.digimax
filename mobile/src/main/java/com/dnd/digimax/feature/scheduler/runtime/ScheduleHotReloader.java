package com.dnd.digimax.feature.scheduler.runtime;

import android.content.Context;
import android.widget.FrameLayout;

import com.dnd.digimax.feature.scheduler.engine.ScheduleEngine;
import com.dnd.digimax.feature.scheduler.model.Schedule;

public class ScheduleHotReloader {

    private final Context context;

    private final FrameLayout root;

    private final ScheduleEngine engine;

    //================================================
    // CONSTRUCTOR
    //================================================

    public ScheduleHotReloader(
            Context context,
            FrameLayout root,
            ScheduleEngine engine
    ) {

        this.context = context;

        this.root = root;

        this.engine = engine;
    }

    //================================================
    // RELOAD
    //================================================

    public void reload(
            Schedule newSchedule
    ) {

        if (newSchedule == null)
            return;

        engine.destroy();

        engine.start(
                context,
                root,
                newSchedule
        );
    }
}