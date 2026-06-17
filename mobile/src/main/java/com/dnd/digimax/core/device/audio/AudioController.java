package com.dnd.digimax.core.device.audio;

import android.content.Context;
import android.media.AudioManager;

public class AudioController {

    private final AudioManager audioManager;

    public AudioController(Context context) {
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    // =========================
    // GET
    // =========================

    public int getVolumePercent() {
        try {
            int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            if (max == 0) return 0;

            return (int) ((current / (float) max) * 100);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getVolumeRaw() {
        try {
            return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            return -1;
        }
    }

    // =========================
    // SET
    // =========================

    public void setVolumePercent(int percent) {
        try {
            percent = clamp(percent, 0, 100);

            int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int target = (int) ((percent / 100f) * max);

            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    target,
                    0 // no sound UI
            );

        } catch (Exception ignored) {
        }
    }

    public void setVolumeRaw(int value) {
        try {
            int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            value = clamp(value, 0, max);

            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    value,
                    0
            );
        } catch (Exception ignored) {
        }
    }

    // =========================
    // MUTE
    // =========================

    public void mute() {
        setVolumePercent(0);
    }

    public void unmute(int percent) {
        setVolumePercent(percent);
    }

    // =========================
    // UTIL
    // =========================

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(val, max));
    }
}