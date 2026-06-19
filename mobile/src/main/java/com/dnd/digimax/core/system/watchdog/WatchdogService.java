package com.dnd.digimax.core.system.watchdog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;

import androidx.core.app.NotificationCompat;

import com.dnd.digimax.core.app.AppController;
import com.dnd.digimax.core.runtime.inspector.RuntimeInspector;
import com.dnd.digimax.core.state.StateBus;
import com.dnd.digimax.core.state.StateEvent;
import com.dnd.digimax.core.state.StateListener;
import com.dnd.digimax.shared.constant.RuntimeEvents;

public class WatchdogService
        extends Service
        implements StateListener {

    //================================================
    // TAG
    //================================================

    private final String strTAG =
            "WatchdogService";

    //================================================
    // LOGGER
    //================================================

    private final LogsSystemWriter logs =
            new LogsSystemWriter();

    //================================================
    // CONFIG
    //================================================

    private static final String CHANNEL_ID =
            "digimax_watchdog";

    private static final long INTERVAL_MS =
            20000;

    private static final long RESTART_COOLDOWN =
            30000;

    //================================================
    // THREAD
    //================================================

    private HandlerThread thread;

    private android.os.Handler handler;

    //================================================
    // INSPECTOR
    //================================================

    private RuntimeInspector runtimeInspector;

    //================================================
    // HEALTH CHECKER
    //================================================

    private PlaybackHealthChecker
            playbackHealthChecker;

    //================================================
    // STATE
    //================================================

    private long lastRestartTime = 0;

    //================================================
    // LIFECYCLE
    //================================================

    @Override
    public void onCreate() {
        super.onCreate();

        startForegroundSafe();

        thread =
                new HandlerThread(
                        "watchdog-thread",
                        Process.THREAD_PRIORITY_BACKGROUND
                );

        thread.start();

        handler =
                new android.os.Handler(
                        thread.getLooper()
                );

        runtimeInspector =
                AppController.get()
                        .getRuntimeInspector();

        StateBus.get().register(this);

        playbackHealthChecker =
                new PlaybackHealthChecker();

        playbackHealthChecker.start();

        startLoop();

        logs.systemWriter(
                strTAG,
                "WATCHDOG_STARTED"
        );
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        StateBus.get().unregister(this);

        if (playbackHealthChecker != null) {

            playbackHealthChecker.stop();
        }

        if (thread != null) {

            thread.quitSafely();
        }

        logs.systemWriter(
                strTAG,
                "WATCHDOG_DESTROYED"
        );
    }

    //================================================
    // LOOP
    //================================================

    private void startLoop() {

        handler.post(watchdogLoop);
    }

    private final Runnable watchdogLoop =
            new Runnable() {

                @Override
                public void run() {

                    try {

                        if (runtimeInspector == null) {

                            runtimeInspector =
                                    AppController.get()
                                            .getRuntimeInspector();
                        }

                        if (runtimeInspector == null) {

                            safeRestartApp(
                                    "NO_INSPECTOR"
                            );

                            return;
                        }

                        boolean healthy =
                                runtimeInspector
                                        .isHealthy();

                        boolean critical =
                                runtimeInspector
                                        .isCriticalFailure();

                        if (critical) {

                            logs.systemWriter(
                                    strTAG,
                                    "CRITICAL"
                            );

                            handleCriticalFailure();

                        } else if (!healthy) {

                            logs.systemWriter(
                                    strTAG,
                                    "DEGRADE"
                            );

                            handleDegradedState();
                        }

                    } catch (Exception e) {

                        logs.systemWriter(
                                strTAG,
                                "WATCHDOG_CRASH : " +
                                        e.getMessage()
                        );

                        safeRestartApp(
                                "WATCHDOG_CRASH"
                        );
                    }

                    handler.postDelayed(
                            this,
                            INTERVAL_MS
                    );
                }
            };

    //================================================
    // STATE EVENTS
    //================================================

    @Override
    public void onStateChanged(StateEvent event) {
        if (event == null)
            return;

        logs.systemWriter(strTAG, "EVENT : " + event.type);
        if (RuntimeEvents.ERROR.equals(event.type)) {
            handleRuntimeError(event);
        }
    }

    //================================================
    // RUNTIME ERROR
    //================================================

    private void handleRuntimeError(StateEvent event) {
        if (event.message == null)
            return;
        logs.systemWriter(strTAG, "RUNTIME_ERROR : " + event.message);
        safeRestartApp(event.message);
    }

    // PLAYBACK TIMEOUT ================================================

    public void onPlaybackTimeout() {
        logs.systemWriter(strTAG, "PLAYBACK_TIMEOUT");

        StateEvent event = new StateEvent();
        event.type = RuntimeEvents.ERROR;
        event.message = "PLAYBACK_TIMEOUT";
        StateBus.get().emit(event);
    }

    //================================================
    // STATE HANDLING
    //================================================

    private void handleCriticalFailure() {
        safeRestartApp("CRITICAL");
    }

    private void handleDegradedState() {
        switch (runtimeInspector.getRecoveryAction()) {
            case RESTART_SERVICE:
                restartService();
                break;
            case ENTER_SAFE_MODE:
                enterSafeMode();
                break;
            case RESTART_APP:
            default:
                safeRestartApp("DEGRADED");
                break;
        }
    }

    //================================================
    // RECOVERY
    //================================================

    private void restartService() {

        stopSelf();

        startService(
                new Intent(
                        this,
                        WatchdogService.class
                )
        );
    }

    private void safeRestartApp(String reason) {
        long now = System.currentTimeMillis();
        if (now - lastRestartTime < RESTART_COOLDOWN) {
            logs.systemWriter(strTAG, "RESTART_BLOCKED : " + reason);
            return;
        }
        lastRestartTime = now;
        logs.systemWriter(strTAG, "RESTART_APP : " + reason);
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        if (intent != null) {
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }

        stopSelf();
    }

    private void enterSafeMode() {
        logs.systemWriter( strTAG, "ENTER_SAFE_MODE" );

        // TODO:
        // fallback minimal UI
        // emergency content
        // maintenance screen
    }

    //================================================
    // FOREGROUND
    //================================================

    private void startForegroundSafe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Digimax Watchdog",
                            NotificationManager.IMPORTANCE_LOW
                    );

            NotificationManager nm =
                    (NotificationManager)
                            getSystemService(
                                    Context.NOTIFICATION_SERVICE
                            );

            if (nm != null) {

                nm.createNotificationChannel(
                        channel
                );
            }

            Notification notification =
                    new NotificationCompat.Builder(
                            this,
                            CHANNEL_ID
                    )
                            .setContentTitle(
                                    "Digimax Running"
                            )
                            .setContentText(
                                    "Watchdog active"
                            )
                            .setSmallIcon(
                                    android.R.drawable.ic_media_play
                            )
                            .build();

            startForeground(
                    1,
                    notification
            );
        }
    }

    //================================================
    // BIND
    //================================================

    @Override
    public IBinder onBind(
            Intent intent
    ) {

        return null;
    }

    //================================================
    // STATIC START
    //================================================

    public static void start(
            Context context
    ) {

        Intent intent =
                new Intent(
                        context,
                        WatchdogService.class
                );

        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {

            context.startForegroundService(
                    intent
            );

        } else {

            context.startService(
                    intent
            );
        }
    }
}