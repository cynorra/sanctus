package com.sanctus.christian.prayer.data.local;

import android.content.Context;
import android.util.Log;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads prayer/saint content from JSON assets (assets/seed/*.json) instead of hardcoded Java,
 * so content fixes/additions only need a new JSON file, not a recompile. A future remote-content
 * step can drop a downloaded JSON file next to these and reuse the same parsing.
 */
public class SeedJsonLoader {
    private static final String TAG = "SeedJsonLoader";

    public static List<PrayerEntity> loadPrayers(Context context) {
        List<PrayerEntity> list = new ArrayList<>();
        JSONArray arr = readJsonArray(context, "seed/prayers.json");
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject o = arr.getJSONObject(i);
                PrayerEntity e = new PrayerEntity();
                e.name = o.getString("name");
                e.category = o.getString("category");
                e.language = o.getString("language");
                e.content = o.getString("content");
                e.isFavorite = false;
                e.lastReadAt = 0;
                list.add(e);
            } catch (JSONException ex) {
                Log.e(TAG, "Skipping malformed prayer entry at index " + i, ex);
            }
        }
        return list;
    }

    public static List<SaintEntity> loadSaints(Context context) {
        List<SaintEntity> list = new ArrayList<>();
        JSONArray arr = readJsonArray(context, "seed/saints.json");
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject o = arr.getJSONObject(i);
                SaintEntity e = new SaintEntity();
                e.name = o.getString("name");
                e.feastDate = o.getString("feastDate");
                e.description = o.getString("description");
                e.patronOf = o.getString("patronOf");
                e.language = o.optString("language", "en");
                e.isFavorite = false;
                list.add(e);
            } catch (JSONException ex) {
                Log.e(TAG, "Skipping malformed saint entry at index " + i, ex);
            }
        }
        return list;
    }

    private static JSONArray readJsonArray(Context context, String assetPath) {
        try (InputStream is = context.getAssets().open(assetPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line).append('\n');
            return new JSONArray(sb.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Failed to load " + assetPath, e);
            return new JSONArray();
        }
    }
}
