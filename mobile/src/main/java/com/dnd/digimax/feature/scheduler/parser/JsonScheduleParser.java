package com.dnd.digimax.feature.scheduler.parser;

import com.dnd.digimax.feature.scheduler.model.Bounds;
import com.dnd.digimax.feature.scheduler.model.PlaylistItem;
import com.dnd.digimax.feature.scheduler.model.Plugin;
import com.dnd.digimax.feature.scheduler.model.Schedule;
import com.dnd.digimax.feature.scheduler.model.Zone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonScheduleParser {

    public Schedule parse(String json) {
        try {JSONObject root = new JSONObject(json);
            Schedule schedule = new Schedule();
            schedule.scheduleId = root.optString("scheduleId");
            schedule.name = root.optString("name");
            JSONArray zonesArray = root.optJSONArray("zones");
            List<Zone> zones = new ArrayList<>();
            if (zonesArray != null) {
                for (int i = 0; i < zonesArray.length();i++) {
                    JSONObject zoneObject = zonesArray.getJSONObject(i);
                    zones.add(parseZone(zoneObject));
                }
            }

            schedule.zones = zones;

            return schedule;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    //================================================
    // ZONE
    //================================================

    private Zone parseZone(
            JSONObject object
    ) throws Exception {

        Zone zone =
                new Zone();

        zone.zoneId =
                object.optString(
                        "zoneId"
                );

        zone.zIndex =
                object.optInt(
                        "zIndex",
                        0
                );

        zone.overlay =
                object.optBoolean(
                        "overlay",
                        false
                );

        zone.overlayDuration =
                object.optLong(
                        "overlayDuration",
                        10000
                );
        zone.overlayInterval =
                object.optLong(
                        "overlayInterval",
                        10000
                );
        zone.pauseWhenOverlay =
                object.optBoolean(
                        "pauseWhenOverlay",
                        false
                );

        JSONObject boundsObject =
                object.optJSONObject(
                        "bounds"
                );

        if (boundsObject != null) {

            zone.bounds =
                    parseBounds(boundsObject);
        }

        JSONObject pluginObject =
                object.optJSONObject(
                        "plugin"
                );

        if (pluginObject != null) {

            zone.plugin =
                    parsePlugin(pluginObject);
        }

        return zone;
    }

    //================================================
    // BOUNDS
    //================================================

    private Bounds parseBounds(
            JSONObject object
    ) {

        Bounds bounds =
                new Bounds();

        bounds.x =
                object.optInt("x");

        bounds.y =
                object.optInt("y");

        bounds.width =
                object.optInt("width");

        bounds.height =
                object.optInt("height");

        return bounds;
    }

    //================================================
    // PLUGIN
    //================================================

    private Plugin parsePlugin(
            JSONObject object
    ) {

        Plugin plugin =
                new Plugin();

        plugin.type =
                object.optString(
                        "type"
                );

        JSONArray playlistArray =
                object.optJSONArray(
                        "playlist"
                );

        List<PlaylistItem> playlist =
                new ArrayList<>();

        if (playlistArray != null) {

            for (int i = 0;
                 i < playlistArray.length();
                 i++) {

                JSONObject itemObject =
                        playlistArray.optJSONObject(i);

                if (itemObject != null) {

                    playlist.add(
                            parsePlaylistItem(
                                    itemObject
                            )
                    );
                }
            }
        }

        plugin.playlist = playlist;

        return plugin;
    }

    //================================================
    // PLAYLIST ITEM
    //================================================

    private PlaylistItem parsePlaylistItem(JSONObject object) {
        PlaylistItem item = new PlaylistItem();
        item.type = object.optString("type");
        item.value = object.optString("value");
        item.duration = object.optLong("duration", 10000);

        return item;
    }
}