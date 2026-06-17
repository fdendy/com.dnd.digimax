package com.dnd.digimax.feature.playback.pop.uploader;

import java.util.List;

public class PopBatchUploader {

    private final PopUploader uploader;

    public PopBatchUploader(
            PopUploader uploader) {

        this.uploader = uploader;
    }

    public boolean upload(
            List<PopPayload> payloads) {

        for (PopPayload payload : payloads) {

            UploadResult result =
                    uploader.upload(
                            payload);

            if (result.getStatus()
                    != UploadStatus.SUCCESS) {

                return false;
            }
        }

        return true;
    }
}