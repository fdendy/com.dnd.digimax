package com.dnd.digimax.app.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dnd.digimax.R;
import com.dnd.digimax.app.player.PlayerActivity;
import com.dnd.digimax.core.app.AppController;
import com.dnd.digimax.core.bootstrap.BootStateManager;
import com.dnd.digimax.core.runtime.health.RuntimeHealthState;
import com.dnd.digimax.core.runtime.lifecycle.RuntimeLifecycleManager;
import com.dnd.digimax.core.runtime.lifecycle.RuntimeState;
import com.dnd.digimax.core.security.verifier.SignatureValidator;
import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

public class SplashActivity extends AppCompatActivity {
    private String strTAG = "SplashActivity";
    private LogsSystemWriter logs = new LogsSystemWriter();
    private AppController appController;
    private BootStateManager bootStateManager;
    private final SignatureValidator signatureValidator = new SignatureValidator();
    private boolean destroyed = false;
    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> {
                        boolean granted = true;
                        for (Boolean value : result.values()) {
                            if (value == null || !value) {
                                granted = false;
                                break;
                            }
                        }
                        if (granted) {
                            checkSystemWritePermission();
                        } else {
                            finish();
                        }
                    }
            );
    private final ActivityResultLauncher<Intent> writeSettingsLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (Settings.System.canWrite(this)) {
                            startBootstrap();
                        } else {
                            finish();
                        }
                    }
            );

    // LIFECYCLE =========================================================
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureWindow();
        setContentView(R.layout.activity_splash);
        initCore();
        validateSignature();
        checkRuntimePermission();
    }
    @Override
    protected void onDestroy() {
        destroyed = true;
        super.onDestroy();
    }

    // WINDOW =========================================================
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

    // CORE =========================================================
    private void initCore() {
        AppController.init(this);
        appController = AppController.get();
        bootStateManager = new BootStateManager(this);
    }
    private void validateSignature() {
        signatureValidator.getAppSignatureHash(this);
    }
    private void checkRuntimePermission() {
        if (hasMediaPermission()) {
            checkSystemWritePermission();
        } else {
            logs.systemWriter(strTAG,"hasMediaPermission(false)");
            requestMediaPermission();
        }
    }
    private boolean hasMediaPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_VIDEO
            ) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void requestMediaPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            permissionLauncher.launch(
                    new String[]{
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_IMAGES
                    }
            );
        } else {
            permissionLauncher.launch(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }
            );
        }
    }
    private void checkSystemWritePermission() {
        if (Settings.System.canWrite(this)) {
            startBootstrap();
            return;
        }
        logs.systemWriter(strTAG,"checkSystemWritePermission(false)");
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,Uri.parse("package:" + getPackageName()));
        writeSettingsLauncher.launch(intent);
    }
    // BOOTSTRAP =========================================================
    private void startBootstrap() {
        appController.initialize(() -> {
            if (destroyed) {
                return;
            }
            validateRuntime();
        });
    }
    // VALIDATION =========================================================
    private void validateRuntime() {
        RuntimeState runtimeState = RuntimeLifecycleManager.getCurrentState();
        RuntimeHealthState healthState = appController.getRuntimeHealthState();
        if(runtimeState != RuntimeState.RUNNING){
            if(runtimeState != RuntimeState.RUNTIME_READY) {
                logs.systemWriter(strTAG, "validateRuntime(runtimeState)" + runtimeState.name());
                finish();
                return;
            }
        }

        if (healthState == RuntimeHealthState.CRITICAL) {
            logs.systemWriter(strTAG,"validateRuntime(healthState)"+healthState.name());
            finish();
            return;
        }

        route(bootStateManager.resolve());
    }
    // ROUTING =========================================================
    private void route(String state) {
        Intent intent = new Intent(this,PlayerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}