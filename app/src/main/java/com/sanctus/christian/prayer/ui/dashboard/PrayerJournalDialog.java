package com.sanctus.christian.prayer.ui.dashboard;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.SanctuaryStore;

import java.util.List;

/**
 * Private Offline Prayer Journal: write personal intentions, mark them "Answered ✓",
 * or remove completed entries.
 */
public final class PrayerJournalDialog {

    private PrayerJournalDialog() {}

    public static void show(Context ctx) {
        if (ctx == null) return;
        List<SanctuaryStore.JournalEntry> entries = SanctuaryStore.getJournalEntries(ctx);
        String[] labels = new String[entries.size() + 1];
        labels[0] = ctx.getString(R.string.journal_add_new);
        for (int i = 0; i < entries.size(); i++) {
            SanctuaryStore.JournalEntry e = entries.get(i);
            labels[i + 1] = (e.answered ? "✓ " : "🕯 ") + e.text;
        }

        new AlertDialog.Builder(ctx)
                .setTitle(R.string.journal_title)
                .setItems(labels, (d, which) -> {
                    if (which == 0) {
                        showAddDialog(ctx);
                    } else {
                        SanctuaryStore.JournalEntry picked = entries.get(which - 1);
                        new AlertDialog.Builder(ctx)
                                .setTitle(picked.answered ? R.string.journal_status_answered : R.string.journal_status_praying)
                                .setMessage(picked.text)
                                .setPositiveButton(picked.answered ? R.string.journal_mark_praying : R.string.journal_mark_answered, (dd, w) -> {
                                    SanctuaryStore.toggleAnswered(ctx, picked.id);
                                    show(ctx);
                                })
                                .setNegativeButton(R.string.journal_delete, (dd, w) -> {
                                    SanctuaryStore.deleteJournalEntry(ctx, picked.id);
                                    show(ctx);
                                })
                                .setNeutralButton(android.R.string.cancel, null)
                                .show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private static void showAddDialog(Context ctx) {
        float dp = ctx.getResources().getDisplayMetrics().density;
        int pad = (int) (20 * dp);
        LinearLayout box = new LinearLayout(ctx);
        box.setPadding(pad, (int) (10 * dp), pad, 0);
        EditText et = new EditText(ctx);
        et.setHint(R.string.journal_input_hint);
        et.setMinLines(3);
        box.addView(et, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        new AlertDialog.Builder(ctx)
                .setTitle(R.string.journal_add_title)
                .setView(box)
                .setPositiveButton(R.string.journal_save, (d, w) -> {
                    String text = et.getText() != null ? et.getText().toString().trim() : "";
                    if (!text.isEmpty()) {
                        SanctuaryStore.addJournalEntry(ctx, text);
                        SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
                        Toast.makeText(ctx, R.string.journal_saved_toast, Toast.LENGTH_SHORT).show();
                        show(ctx);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
