package com.dnd.digimax.plugin.impl.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.dnd.digimax.core.state.StateBus;
import com.dnd.digimax.core.state.StateEvent;
import com.dnd.digimax.feature.content.resolver.ContentResolver;
import com.dnd.digimax.feature.scheduler.model.PlaylistItem;
import com.dnd.digimax.feature.scheduler.model.Plugin;
import com.dnd.digimax.plugin.base.PluginRenderer;
import com.dnd.digimax.shared.constant.RuntimeEvents;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayerRenderer implements PluginRenderer {

    // PLAYER ================================================
    private ExoPlayer player;

    private PlayerView playerView;
    private final ContentResolver resolver = new ContentResolver();
    // DATA ================================================
    private Plugin plugin;
    private Context context;
    // CREATE VIEW ================================================

    @Override
    public View createView(Context context) {
        this.context = context;
        player = new ExoPlayer.Builder(context).build();
        playerView =
                new PlayerView(context);

        playerView.setUseController(false);

        playerView.setPlayer(player);

        buildPlaylist();

        return playerView;
    }

    //================================================
    // INITIALIZE
    //================================================

    @Override
    public void initialize(Plugin plugin) {

        this.plugin = plugin;
    }

    //================================================
    // PLAYLIST
    //================================================

    private void buildPlaylist() {

        if (plugin == null) return;

        if (plugin.playlist == null) return;

        List<MediaItem> mediaItems =
                new ArrayList<>();

        for (PlaylistItem item
                : plugin.playlist) {

            MediaItem mediaItem =
                    createMediaItem(item);

            if (mediaItem != null) {

                mediaItems.add(mediaItem);
            }
        }

        player.setMediaItems(mediaItems);

        player.setRepeatMode(
                Player.REPEAT_MODE_ALL
        );

        player.prepare();
    }

    //================================================
    // MEDIA ITEM
    //================================================

    @OptIn(markerClass = UnstableApi.class)
    private MediaItem createMediaItem(
            PlaylistItem item
    ) {
        String source =
                resolver.resolve(
                        context,
                        item.value
                );

        if ("video".equals(item.type)) {
            return new MediaItem.Builder()
                    .setUri(Uri.parse(source))
                    .build();
        }

        if ("image".equals(item.type)) {
            return new MediaItem.Builder()
                    .setUri(Uri.parse(source))
                    .setImageDurationMs(item.duration)
                    .build();
        }

        return null;
    }

    //================================================
    // PLAYBACK
    //================================================

    @Override
    public void play() {
        if (player != null) {
            player.play();
            StateEvent event = new StateEvent();
            event.type = RuntimeEvents.PLAY;
            event.pluginType = "exoplayer";
            StateBus.get().emit(event);
        }
    }

    @Override
    public void pause() {

        if (player != null) {
            player.pause();
            StateEvent event = new StateEvent();
            event.type = RuntimeEvents.PAUSE;
            event.pluginType = "exoplayer";
            StateBus.get().emit(event);
        }
    }

    @Override
    public void stop() {
        if (player != null) {
            player.stop();
        }
    }

    @Override
    public void release() {
        if (player != null) {
            player.release();
        }
    }
}