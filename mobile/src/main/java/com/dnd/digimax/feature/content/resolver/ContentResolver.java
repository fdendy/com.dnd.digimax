package com.dnd.digimax.feature.content.resolver;

import android.content.Context;

import com.dnd.digimax.feature.content.cache.ContentCacheManager;
import com.dnd.digimax.feature.content.validator.ContentValidator;

import java.io.File;

public class ContentResolver {

    private final ContentCacheManager cacheManager =
            new ContentCacheManager();

    private final ContentValidator validator =
            new ContentValidator();

    //================================================
    // RESOLVE
    //================================================

    public String resolve(
            Context context,
            String source
    ) {

        // RESOURCE
        if (source.startsWith(
                "android.resource://"
        )) {

            return source;
        }

        // REMOTE URL
        if (source.startsWith(
                "http"
        )) {

            String fileName =
                    extractFileName(source);

            File file =
                    cacheManager.getContentFile(
                            context,
                            fileName
                    );

            if (validator.isValid(file)) {

                return file.getAbsolutePath();
            }

            return source;
        }

        return source;
    }

    //================================================
    // FILE NAME
    //================================================

    private String extractFileName(
            String url
    ) {

        int index =
                url.lastIndexOf("/");

        if (index == -1)
            return url;

        return url.substring(index + 1);
    }
}