package com.dnd.digimax.feature.content.cache;

import android.content.Context;

import java.io.File;

public class ContentCacheManager {

    //================================================
    // CACHE DIR
    //================================================

    public File getCacheDirectory(
            Context context
    ) {

        File dir =
                new File(
                        context.getFilesDir(),
                        "digimax/content"
                );

        if (!dir.exists()) {

            dir.mkdirs();
        }

        return dir;
    }

    //================================================
    // CONTENT FILE
    //================================================

    public File getContentFile(
            Context context,
            String fileName
    ) {

        return new File(
                getCacheDirectory(context),
                fileName
        );
    }

    //================================================
    // EXISTS
    //================================================

    public boolean exists(
            Context context,
            String fileName
    ) {

        return getContentFile(
                context,
                fileName
        ).exists();
    }

    //================================================
    // DELETE
    //================================================

    public void delete(
            Context context,
            String fileName
    ) {

        File file =
                getContentFile(
                        context,
                        fileName
                );

        if (file.exists()) {

            file.delete();
        }
    }
}