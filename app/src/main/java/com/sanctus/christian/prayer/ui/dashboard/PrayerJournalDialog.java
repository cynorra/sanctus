package com.sanctus.christian.prayer.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.SanctuaryStore;

import java.util.List;

/**
 * 2027+ Private Offline Prayer Journal — frameless obsidian-glass sanctuary
 * sheet
 * with status pills, one-tap answered toggle, and clean offline storage.
 */
public final class PrayerJournalDialog {

    private PrayerJournalDialog() {
    }

    public static void show(Context ctx) {
        if (ctx == null)
            return;
        float dp = ctx.getResources().getDisplayMetrics().density;
        List<SanctuaryStore.JournalEntry> entries = SanctuaryStore.getJournalEntries(ctx);

        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout root = createSheetContainer(ctx, dp);

        TextView eyebrow = new TextView(ctx);
        eyebrow.setText("SANCTUARY JOURNAL");
        eyebrow.setTextSize(11f);
        eyebrow.setLetterSpacing(0.18f);
        eyebrow.setTypeface(Typeface.DEFAULT_BOLD);
        eyebrow.setTextColor(0xFFE6BC5C);
        root.addView(eyebrow);

        TextView title = new TextView(ctx);
        title.setText(R.string.journal_title);
        title.setTextSize(24f);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setTextColor(0xFFF7F3FF);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = (int) (4 * dp);
        titleLp.bottomMargin = (int) (14 * dp);
        root.addView(title, titleLp);

        TextView btnNew = createGoldPill(ctx, dp, ctx.getString(R.string.journal_add_new));
        btnNew.setOnClickListener(v -> {
            dialog.dismiss();
            showAddDialog(ctx);
        });
        LinearLayout.LayoutParams newLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (46 * dp));
        newLp.bottomMargin = (int) (14 * dp);
        root.addView(btnNew, newLp);

        ScrollView scroll = new ScrollView(ctx);
        LinearLayout list = new LinearLayout(ctx);
        list.setOrientation(LinearLayout.VERTICAL);

        if (entries.isEmpty()) {
            TextView empty = new TextView(ctx);
            empty.setText(R.string.journal_input_hint);
            empty.setTextSize(13.5f);
            empty.setTextColor(0xFFA199BD);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding((int) (12 * dp), (int) (24 * dp), (int) (12 * dp), (int) (24 * dp));
            list.addView(empty);
        } else {
            for (SanctuaryStore.JournalEntry entry : entries) {
                LinearLayout card = new LinearLayout(ctx);
                card.setOrientation(LinearLayout.VERTICAL);
                card.setPadding((int) (16 * dp), (int) (14 * dp), (int) (16 * dp), (int) (14 * dp));
                GradientDrawable cardBg = new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[] { 0x1EFFFFFF, 0x0CFFFFFF });
                cardBg.setCornerRadius(18 * dp);
                cardBg.setStroke((int) (1 * dp), entry.answered ? 0x555FC48A : 0x33E6BC5C);
                card.setBackground(cardBg);

                TextView status = new TextView(ctx);
                status.setText(entry.answered
                        ? "✓ " + ctx.getString(R.string.journal_status_answered)
                        : "🕯 " + ctx.getString(R.string.journal_status_praying));
                status.setTextSize(11f);
                status.setLetterSpacing(0.08f);
                status.setTypeface(Typeface.DEFAULT_BOLD);
                status.setTextColor(entry.answered ? 0xFF5FC48A : 0xFFE6BC5C);
                card.addView(status);

                TextView body = new TextView(ctx);
                body.setText(entry.text);
                body.setTextSize(14.5f);
                body.setTextColor(0xFFF7F3FF);
                body.setLineSpacing(0f, 1.32f);
                LinearLayout.LayoutParams bodyLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                bodyLp.topMargin = (int) (6 * dp);
                card.addView(body, bodyLp);

                LinearLayout actions = new LinearLayout(ctx);
                actions.setOrientation(LinearLayout.HORIZONTAL);
                actions.setGravity(Gravity.END);
                LinearLayout.LayoutParams actLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                actLp.topMargin = (int) (10 * dp);

                TextView btnToggle = createMiniPill(ctx, dp,
                        ctx.getString(entry.answered ? R.string.journal_mark_praying : R.string.journal_mark_answered),
                        0xFFE6BC5C, 0x22E6BC5C);
                btnToggle.setOnClickListener(v -> {
                    SanctuaryStore.toggleAnswered(ctx, entry.id);
                    dialog.dismiss();
                    show(ctx);
                });
                actions.addView(btnToggle);

                View spacer = new View(ctx);
                actions.addView(spacer, new LinearLayout.LayoutParams((int) (8 * dp), 1));

                TextView btnDel = createMiniPill(ctx, dp,
                        ctx.getString(R.string.journal_delete),
                        0xFFE5788C, 0x22E5788C);
                btnDel.setOnClickListener(v -> {
                    SanctuaryStore.deleteJournalEntry(ctx, entry.id);
                    dialog.dismiss();
                    show(ctx);
                });
                actions.addView(btnDel);

                card.addView(actions, actLp);

                LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cardLp.bottomMargin = (int) (10 * dp);
                list.addView(card, cardLp);
            }
        }

        scroll.addView(list);
        LinearLayout.LayoutParams scrollLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Math.min((int) (340 * dp), entries.isEmpty() ? (int) (96 * dp) : (int) (340 * dp)));
        root.addView(scroll, scrollLp);

        TextView btnClose = createGhostPill(ctx, dp, ctx.getString(android.R.string.cancel));
        btnClose.setOnClickListener(v -> dialog.dismiss());
        LinearLayout.LayoutParams closeLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (44 * dp));
        closeLp.topMargin = (int) (12 * dp);
        root.addView(btnClose, closeLp);

        presentDialog(dialog, root, dp);
    }

    private static void showAddDialog(Context ctx) {
        float dp = ctx.getResources().getDisplayMetrics().density;
        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout root = createSheetContainer(ctx, dp);

        TextView title = new TextView(ctx);
        title.setText(R.string.journal_add_title);
        title.setTextSize(22f);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setTextColor(0xFFF7F3FF);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = (int) (14 * dp);
        root.addView(title, titleLp);

        EditText et = new EditText(ctx);
        et.setHint(R.string.journal_input_hint);
        et.setHintTextColor(0xFF8B83A8);
        et.setTextColor(0xFFF7F3FF);
        et.setTextSize(15f);
        et.setMinLines(4);
        et.setGravity(Gravity.TOP | Gravity.START);
        int pad = (int) (14 * dp);
        et.setPadding(pad, pad, pad, pad);
        GradientDrawable inputBg = new GradientDrawable();
        inputBg.setColor(0x18FFFFFF);
        inputBg.setCornerRadius(16 * dp);
        inputBg.setStroke((int) (1 * dp), 0x38E6BC5C);
        et.setBackground(inputBg);
        root.addView(et, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout row = new LinearLayout(ctx);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowLp.topMargin = (int) (16 * dp);

        TextView btnCancel = createGhostPill(ctx, dp, ctx.getString(android.R.string.cancel));
        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            show(ctx);
        });
        LinearLayout.LayoutParams cLp = new LinearLayout.LayoutParams(0, (int) (46 * dp), 1f);
        cLp.rightMargin = (int) (10 * dp);
        row.addView(btnCancel, cLp);

        TextView btnSave = createGoldPill(ctx, dp, ctx.getString(R.string.journal_save));
        btnSave.setOnClickListener(v -> {
            String text = et.getText() != null ? et.getText().toString().trim() : "";
            if (!text.isEmpty()) {
                SanctuaryStore.addJournalEntry(ctx, text);
                SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
                Toast.makeText(ctx, R.string.journal_saved_toast, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                show(ctx);
            }
        });
        row.addView(btnSave, new LinearLayout.LayoutParams(0, (int) (46 * dp), 1f));

        root.addView(row, rowLp);
        presentDialog(dialog, root, dp);
    }

    private static LinearLayout createSheetContainer(Context ctx, float dp) {
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (22 * dp);
        root.setPadding(pad, pad, pad, pad);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF131622);
        bg.setCornerRadius(26 * dp);
        bg.setStroke((int) (1.2f * dp), 0x44D4AF37);
        root.setBackground(bg);
        return root;
    }

    private static TextView createGoldPill(Context ctx, float dp, String text) {
        TextView tv = new TextView(ctx);
        tv.setText(text);
        tv.setTextSize(14f);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(0xFF07090E);
        tv.setGravity(Gravity.CENTER);
        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { 0xFFF3C068, 0xFFD4AF37 });
        bg.setCornerRadius(23 * dp);
        tv.setBackground(bg);
        return tv;
    }

    private static TextView createGhostPill(Context ctx, float dp, String text) {
        TextView tv = new TextView(ctx);
        tv.setText(text);
        tv.setTextSize(13.5f);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(0xFFE6E1D6);
        tv.setGravity(Gravity.CENTER);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF11141E);
        bg.setCornerRadius(22 * dp);
        bg.setStroke((int) (1 * dp), 0x1EFFFFFF);
        tv.setBackground(bg);
        return tv;
    }

    private static TextView createMiniPill(Context ctx, float dp, String text, int textColor, int bgColor) {
        TextView tv = new TextView(ctx);
        tv.setText(text);
        tv.setTextSize(11.5f);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(textColor);
        tv.setPadding((int) (12 * dp), (int) (6 * dp), (int) (12 * dp), (int) (6 * dp));
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(bgColor);
        bg.setCornerRadius(14 * dp);
        tv.setBackground(bg);
        return tv;
    }

    private static void presentDialog(Dialog dialog, View root, float dp) {
        dialog.setContentView(root);
        Window win = dialog.getWindow();
        if (win != null) {
            win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = (int) (root.getContext().getResources().getDisplayMetrics().widthPixels - 36 * dp);
            win.setAttributes(lp);
        }
        dialog.show();
    }
}
