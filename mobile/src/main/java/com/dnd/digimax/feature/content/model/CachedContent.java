package com.dnd.digimax.feature.content.model;

import java.io.File;

public class CachedContent {

    public String contentId;

    public String originalUrl;

    public String localPath;

    public long fileSize;

    public long lastModified;

    public boolean valid;

    public File file;
}