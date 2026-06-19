package com.dnd.digimax.core.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.dnd.digimax.core.runtime.health.RuntimeHealthManager;
import com.dnd.digimax.core.runtime.health.RuntimeHealthState;
import com.dnd.digimax.core.runtime.health.RuntimeRiskAnalyzer;
import com.dnd.digimax.core.runtime.inspector.RuntimeInspector;
import com.dnd.digimax.core.runtime.lifecycle.RuntimeLifecycleManager;
import com.dnd.digimax.core.runtime.lifecycle.RuntimeState;
import com.dnd.digimax.core.security.coordinator.SecurityCoordinator;
import com.dnd.digimax.core.state.GlobalStateBus;
import com.dnd.digimax.core.system.SystemInfoProvider;
import com.dnd.digimax.core.system.watchdog.WatchdogService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AppController {
    private static AppController instance;
    private final Context appContext;
    // CORE COMPONENTS =========================================================
    private final RuntimeInspector runtimeInspector;
    private final RuntimeRiskAnalyzer runtimeRiskAnalyzer;
    private final RuntimeHealthManager runtimeHealthManager;
    private final SecurityCoordinator securityCoordinator;
    private final SystemInfoProvider systemInfoProvider;

    // THREADING =========================================================
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // STATE =========================================================
    private volatile boolean initialized = false;

    // CALLBACK =========================================================
    public interface Callback {
        void onComplete();
    }

    // CONSTRUCTOR =========================================================
    private AppController(Context context) {
        this.appContext = context.getApplicationContext();
        runtimeInspector = new RuntimeInspector(appContext);
        runtimeRiskAnalyzer = new RuntimeRiskAnalyzer(runtimeInspector);
        runtimeHealthManager = new RuntimeHealthManager(runtimeRiskAnalyzer);
        securityCoordinator = new SecurityCoordinator(appContext);
        systemInfoProvider = new SystemInfoProvider(appContext);
    }
    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new AppController(context);
        }
    }
    public static AppController get() {
        if (instance == null) {
            throw new IllegalStateException(
                    "AppController not initialized"
            );
        }
        return instance;
    }

    // INITIALIZATION FLOW =========================================================
    public synchronized void initialize(Callback callback) {
        if (initialized) {
            if (callback != null) {
                mainHandler.post(callback::onComplete);
            }
            return;
        }
        executorService.execute(() -> {
            try {
                RuntimeLifecycleManager.moveTo(RuntimeState.INITIALIZING);
                publish("app_initializing",true);
                RuntimeLifecycleManager.moveTo(RuntimeState.SECURITY_CHECK);
                boolean secure = securityCoordinator.checkStartupIntegrity();
                if (!secure) {
                    RuntimeLifecycleManager.moveTo(RuntimeState.SAFE_MODE);
                    publish("security_startup_failed",true);
                    return;
                }
                systemInfoProvider.load();
                runtimeHealthManager.update();
                publish("runtime_health",runtimeHealthManager.getCurrentState());
                RuntimeLifecycleManager.moveTo(RuntimeState.RUNTIME_READY);
                publish("runtime_ready",true);
                WatchdogService.start(appContext);
                RuntimeLifecycleManager.moveTo(RuntimeState.RUNNING);
                publish("runtime_running",true);
                initialized = true;
            } catch (Exception e) {
                e.printStackTrace();
                RuntimeLifecycleManager.moveTo(RuntimeState.RECOVERY);
                publish("runtime_boot_failure", true);
            }
            if (callback != null) {
                mainHandler.post(callback::onComplete);
            }
        });
    }

    // GLOBAL STATE =========================================================
    private void publish(String key, Object value) {
        GlobalStateBus.getInstance().publish(
                        key,
                        value
                );
    }

    // PUBLIC GETTER =========================================================
    public RuntimeInspector getRuntimeInspector() {
        return runtimeInspector;
    }
    public RuntimeHealthManager getRuntimeHealthManager() {
        return runtimeHealthManager;
    }
    public RuntimeHealthState getRuntimeHealthState() {
        return runtimeHealthManager.getCurrentState();
    }
    public SecurityCoordinator getSecurityCoordinator() {
        return securityCoordinator;
    }
    public SystemInfoProvider getSystemInfoProvider() {
        return systemInfoProvider;
    }
    public boolean isInitialized() {
        return initialized;
    }
}
