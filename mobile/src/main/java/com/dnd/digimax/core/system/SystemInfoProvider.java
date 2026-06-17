package com.dnd.digimax.core.system;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Locale;

public class SystemInfoProvider {

    private final Context context;

    // CACHE =============================

    private String deviceId;
    private String manufacturer;
    private String model;
    private String osVersion;
    private int sdkInt;
    private String locale;
    private String localIp;

    private boolean initialized = false;

    // CONSTRUCTOR =============================
    public SystemInfoProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    // BOOT LOAD =============================
    public synchronized void load() {
        if (initialized) return;
        deviceId = resolveDeviceId();
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        osVersion = Build.VERSION.RELEASE;
        sdkInt = Build.VERSION.SDK_INT;
        locale = Locale.getDefault().toString();
        localIp = resolveLocalIp();
        initialized = true;
        Log.d("SystemInfo", "Loaded: " + toString());
    }

    // DEVICE ID =============================
    private String resolveDeviceId() {
        try {
            return Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
        } catch (Exception e) {
            return "UNKNOWN_DEVICE";
        }
    }

    // NETWORK ID (SAFE FILTERED) =============================
    private String resolveLocalIp() {

        try {
            for (NetworkInterface ni :
                    Collections.list(NetworkInterface.getNetworkInterfaces())) {

                if (!ni.isUp() || ni.isLoopback()) continue;

                for (InetAddress addr :
                        Collections.list(ni.getInetAddresses())) {

                    String ip = addr.getHostAddress();

                    if (ip == null) continue;

                    // filter IPv6 + loopback
                    if (!addr.isLoopbackAddress()
                            && ip.contains(".")
                            && !ip.startsWith("10.0.2") // emulator filter
                    ) {
                        return ip;
                    }
                }
            }
        } catch (Exception ignored) {}

        return "0.0.0.0";
    }

    // SECURITY SIGNALS (REALISTIC LAYER HOOK) =============================
    public boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("emulator")
                || Build.MODEL.contains("Emulator")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic");
    }
    public boolean isRooted() {
        // placeholder for future SecurityChecker integration
        return false;
    }
    public boolean isDebuggableBuild() {
        return (context.getApplicationInfo().flags &
                android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    // GUARD =============================
    private void ensureReady() {
        if (!initialized) {
            throw new IllegalStateException("SystemInfoProvider not initialized. Call load() first.");
        }
    }

    // GETTERS =============================
    public String getDeviceId() {
        ensureReady();
        return deviceId;
    }
    public String getManufacturer() {
        ensureReady();
        return manufacturer;
    }
    public String getModel() {
        ensureReady();
        return model;
    }
    public String getOsVersion() {
        ensureReady();
        return osVersion;
    }
    public int getSdkInt() {
        ensureReady();
        return sdkInt;
    }
    public String getLocale() {
        ensureReady();
        return locale;
    }
    public String getLocalIp() {
        ensureReady();
        return localIp;
    }
    public boolean isInitialized() {
        return initialized;
    }

    // DEBUG / FINGERPRINT  =============================
    @Override
    public String toString() {
        return "SystemInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", sdkInt=" + sdkInt +
                ", locale='" + locale + '\'' +
                ", ip='" + localIp + '\'' +
                '}';
    }
}