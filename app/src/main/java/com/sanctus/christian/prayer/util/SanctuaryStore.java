package com.sanctus.christian.prayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Offline persistence for Sanctus v2.0.0 features:
 * - Private Prayer Journal (intentions + "Answered ✓" tracking)
 * - Multi-Day Guided Devotional Plans (7-Day Peace, 7-Day Gratitude, 9-Day Novena)
 */
public final class SanctuaryStore {

    private static final String PREFS = "sanctus_v2_sanctuary";
    private static final String K_JOURNAL = "prayer_journal_json";
    private static final String K_PLAN_PREFIX = "plan_day_";

    public static final class JournalEntry {
        public final long id;
        public final String text;
        public final long createdAt;
        public boolean answered;

        public JournalEntry(long id, String text, long createdAt, boolean answered) {
            this.id = id;
            this.text = text;
            this.createdAt = createdAt;
            this.answered = answered;
        }
    }

    private SanctuaryStore() {}

    private static SharedPreferences prefs(Context ctx) {
        return ctx.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static List<JournalEntry> getJournalEntries(Context ctx) {
        List<JournalEntry> list = new ArrayList<>();
        String raw = prefs(ctx).getString(K_JOURNAL, "[]");
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                list.add(new JournalEntry(
                        o.optLong("id", i + 1L),
                        o.optString("text", ""),
                        o.optLong("createdAt", System.currentTimeMillis()),
                        o.optBoolean("answered", false)
                ));
            }
        } catch (Exception ignored) {
        }
        return list;
    }

    public static void addJournalEntry(Context ctx, String text) {
        if (text == null || text.trim().isEmpty()) return;
        List<JournalEntry> list = getJournalEntries(ctx);
        list.add(0, new JournalEntry(System.currentTimeMillis(), text.trim(), System.currentTimeMillis(), false));
        saveJournal(ctx, list);
    }

    public static void toggleAnswered(Context ctx, long id) {
        List<JournalEntry> list = getJournalEntries(ctx);
        for (JournalEntry e : list) {
            if (e.id == id) {
                e.answered = !e.answered;
                break;
            }
        }
        saveJournal(ctx, list);
    }

    public static void deleteJournalEntry(Context ctx, long id) {
        List<JournalEntry> list = getJournalEntries(ctx);
        list.removeIf(e -> e.id == id);
        saveJournal(ctx, list);
    }

    private static void saveJournal(Context ctx, List<JournalEntry> list) {
        JSONArray arr = new JSONArray();
        try {
            for (JournalEntry e : list) {
                JSONObject o = new JSONObject();
                o.put("id", e.id);
                o.put("text", e.text);
                o.put("createdAt", e.createdAt);
                o.put("answered", e.answered);
                arr.put(o);
            }
        } catch (Exception ignored) {
        }
        prefs(ctx).edit().putString(K_JOURNAL, arr.toString()).apply();
    }

    public static int getPlanCompletedDays(Context ctx, String planId) {
        return prefs(ctx).getInt(K_PLAN_PREFIX + planId, 0);
    }

    public static int completeNextPlanDay(Context ctx, String planId, int totalDays) {
        int cur = getPlanCompletedDays(ctx, planId);
        int next = Math.min(totalDays, cur + 1);
        prefs(ctx).edit().putInt(K_PLAN_PREFIX + planId, next).apply();
        return next;
    }

    public static void resetPlan(Context ctx, String planId) {
        prefs(ctx).edit().putInt(K_PLAN_PREFIX + planId, 0).apply();
    }
}
