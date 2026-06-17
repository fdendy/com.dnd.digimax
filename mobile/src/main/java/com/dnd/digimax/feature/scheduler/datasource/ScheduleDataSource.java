package com.dnd.digimax.feature.scheduler.datasource;

import android.content.Context;

import java.io.InputStream;

public class ScheduleDataSource {

    public String loadFromAssets(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}