package com.dnd.digimax.feature.playback.pop.batch;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;
import com.dnd.digimax.feature.playback.pop.mapper.PopMapper;
import com.dnd.digimax.feature.playback.pop.provider.PopProvider;
import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;
import com.dnd.digimax.feature.playback.pop.uploader.PopUploader;
import com.dnd.digimax.feature.playback.pop.uploader.UploadResult;
import com.dnd.digimax.feature.playback.pop.uploader.UploadStatus;

import org.json.JSONArray;

import java.util.List;

public class BatchDispatcher {

    private final BatchPolicy policy;

    private final BatchBuilder builder;

    private final BatchCompressor compressor;

    public BatchDispatcher() {

        policy =
                new BatchPolicy();

        builder =
                new BatchBuilder();

        compressor =
                new BatchCompressor();
    }

    public void add(
            ProofOfPlayRecord record) {

        builder.add(
                record
        );
    }

    public boolean shouldDispatch() {

        return builder.size()
                >= policy.getMaxRecords();
    }

    public UploadResult dispatch(
            String deviceId,
            PopProvider provider,
            PopUploader uploader) {

        JSONArray payloads =
                new JSONArray();

        List<ProofOfPlayRecord> records =
                builder.build();

        for (ProofOfPlayRecord record
                : records) {

            PopPayload payload =
                    PopMapper.toPayload(
                            deviceId,
                            record
                    );

            payloads.put(
                    provider.buildPayload(
                            payload
                    )
            );
        }

        byte[] compressed =
                compressor.compress(
                        payloads
                );

        builder.clear();

        return new UploadResult(
                UploadStatus.SUCCESS,
                "Batch size="
                        + compressed.length
        );
    }
}