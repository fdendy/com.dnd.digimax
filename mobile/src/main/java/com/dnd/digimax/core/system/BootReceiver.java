package com.dnd.digimax.core.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.dnd.digimax.app.splash.SplashActivity;
import com.dnd.digimax.core.bootstrap.BootPolicy;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null) return;

        String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)
                || Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)) {

            Log.d(TAG, "BOOT EVENT: " + action);

            BootPolicy policy = new BootPolicy(context);

            if (!policy.shouldStartOnBoot()) {
                Log.d(TAG, "BootPolicy blocked start");
                return;
            }

            startApp(context);
        }
    }

    private void startApp(Context context) {

        Intent i = new Intent(context, SplashActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(i);
    }
}