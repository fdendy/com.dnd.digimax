package com.dnd.digimax.app.player;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dnd.digimax.core.state.StateBus;
import com.dnd.digimax.core.state.StateEvent;
import com.dnd.digimax.core.state.StateListener;
import com.dnd.digimax.feature.scheduler.datasource.ScheduleDataSource;
import com.dnd.digimax.feature.scheduler.engine.ScheduleEngine;
import com.dnd.digimax.feature.scheduler.model.Schedule;
import com.dnd.digimax.feature.scheduler.parser.JsonScheduleParser;
import com.dnd.digimax.feature.scheduler.parser.ScheduleParser;
import com.dnd.digimax.feature.scheduler.runtime.ScheduleHotReloader;

public class PlayerActivity
        extends AppCompatActivity
        implements StateListener {

    // VIEW ================================================
    private FrameLayout rootContainer;

    // ENGINE ================================================
    private ScheduleEngine scheduleEngine;
    private ScheduleHotReloader hotReloader;
    // CREATE ================================================
    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);
        configureWindow();
        rootContainer = new FrameLayout(this);
        setContentView(rootContainer);
        StateBus.get().register(this);

        ScheduleDataSource dataSource = new ScheduleDataSource();
        String json = dataSource.loadFromAssets(this,"schedule.json");

        JsonScheduleParser parser = new JsonScheduleParser();
        Schedule schedule = parser.parse(json);
        scheduleEngine = new ScheduleEngine();
        scheduleEngine.start(this,rootContainer,schedule);
        hotReloader = new ScheduleHotReloader(this,rootContainer,scheduleEngine);
        new Handler().postDelayed(() -> {
            ScheduleParser alternativeParser = new ScheduleParser();
            Schedule newSchedule = alternativeParser.loadAlternative(this);
            hotReloader.reload(newSchedule);
        }, 20000);
    }

    //================================================
    // DESTROY
    //================================================

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StateBus.get().unregister(this);
        if (scheduleEngine != null) {
            scheduleEngine.stop();
        }
    }

    //================================================
    // STATE EVENT
    //================================================

    @Override
    public void onStateChanged(
            StateEvent event
    ) {

        Log.d(
                "DIGIMAX",
                event.type +
                        " | " +
                        event.pluginType +
                        " | " +
                        event.zoneId
        );
    }

    //================================================
    // WINDOW
    //================================================

    private void configureWindow() {

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        View decorView =
                getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}