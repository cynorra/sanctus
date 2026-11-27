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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.SanctuaryStore;

/**
 * 2027+ Multi-Day Guided Devotional Plans (7-Day Peace, 7-Day Psalms of Gratitude, 9-Day Novena)
 * rendered as a frameless obsidian-glass sanctuary sheet with champagne-gold progress bars.
 */
public final class GuidedPlansDialog {

    private GuidedPlansDialog() {}

    public static void show(Context ctx) {
        if (ctx == null) return;
        float dp = ctx.getResources().getDisplayMetrics().density;

        int p1 = SanctuaryStore.getPlanCompletedDays(ctx, "peace_7");
        int p2 = SanctuaryStore.getPlanCompletedDays(ctx, "gratitude_7");
        int p3 = SanctuaryStore.getPlanCompletedDays(ctx, "novena_9");

        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout root = createSheetContainer(ctx, dp);

        TextView eyebrow = new TextView(ctx);
        eyebrow.setText("GUIDED JOURNEYS");
        eyebrow.setTextSize(11f);
        eyebrow.setLetterSpacing(0.18f);
        eyebrow.setTypeface(Typeface.DEFAULT_BOLD);
        eyebrow.setTextColor(0xFFE6BC5C);
        root.addView(eyebrow);

        TextView title = new TextView(ctx);
        title.setText(R.string.plans_dialog_title);
        title.setTextSize(24f);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setTextColor(0xFFF7F3FF);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = (int) (4 * dp);
        titleLp.bottomMargin = (int) (16 * dp);
        root.addView(title, titleLp);

        root.addView(createPlanCard(ctx, dp, "🕊️", ctx.getString(R.string.plan_peace_name), p1, 7, () -> {
            dialog.dismiss();
            openPlan(ctx, "peace_7", 7,
                    ctx.getString(R.string.plan_peace_name),
                    ctx.getString(R.string.plan_peace_reflection));
        }));

        root.addView(createPlanCard(ctx, dp, "✨", ctx.getString(R.string.plan_gratitude_name), p2, 7, () -> {
            dialog.dismiss();
            openPlan(ctx, "gratitude_7", 7,
                    ctx.getString(R.string.plan_gratitude_name),
                    ctx.getString(R.string.plan_gratitude_reflection));
        }));

        root.addView(createPlanCard(ctx, dp, "🕯️", ctx.getString(R.string.plan_novena_name), p3, 9, () -> {
            dialog.dismiss();
            openPlan(ctx, "novena_9", 9,
                    ctx.getString(R.string.plan_novena_name),
                    ctx.getString(R.string.plan_novena_reflection));
        }));

        TextView btnClose = createGhostPill(ctx, dp, ctx.getString(android.R.string.cancel));
        btnClose.setOnClickListener(v -> dialog.dismiss());
        LinearLayout.LayoutParams closeLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (44 * dp));
        closeLp.topMargin = (int) (8 * dp);
        root.addView(btnClose, closeLp);

        presentDialog(dialog, root, dp);
    }

    private static View createPlanCard(Context ctx, float dp, String icon, String name,
                                       int done, int total, Runnable onClick) {
        LinearLayout card = new LinearLayout(ctx);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding((int) (16 * dp), (int) (14 * dp), (int) (16 * dp), (int) (14 * dp));
        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{0x22E6BC5C, 0x1014122B});
        bg.setCornerRadius(18 * dp);
        bg.setStroke((int) (1 * dp), 0x44E6BC5C);
        card.setBackground(bg);

        LinearLayout header = new LinearLayout(ctx);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView tvTitle = new TextView(ctx);
        tvTitle.setText(icon + "  " + name);
        tvTitle.setTextSize(15.5f);
        tvTitle.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvTitle.setTextColor(0xFFF7F3FF);
        header.addView(tvTitle, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView badge = new TextView(ctx);
        badge.setText(done + " / " + total);
        badge.setTextSize(12f);
        badge.setTypeface(Typeface.DEFAULT_BOLD);
        badge.setTextColor(done >= total ? 0xFF5FC48A : 0xFFE6BC5C);
        header.addView(badge);

        card.addView(header);

        // Sleek progress bar
        FrameLayout track = new FrameLayout(ctx);
        GradientDrawable trackBg = new GradientDrawable();
        trackBg.setColor(0x26FFFFFF);
        trackBg.setCornerRadius(4 * dp);
        track.setBackground(trackBg);
        LinearLayout.LayoutParams trackLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (6 * dp));
        trackLp.topMargin = (int) (10 * dp);

        View fill = new View(ctx);
        GradientDrawable fillBg = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                done >= total ? new int[]{0xFF5FC48A, 0xFF3DA66B} : new int[]{0xFFF4D584, 0xFFC99A3B});
        fillBg.setCornerRadius(4 * dp);
        fill.setBackground(fillBg);
        float pct = total > 0 ? Math.min(1f, (float) done / total) : 0f;
        int fillWidth = Math.max(done > 0 ? (int) (18 * dp) : 0, (int) (pct * 260 * dp));
        track.addView(fill, new FrameLayout.LayoutParams(fillWidth, FrameLayout.LayoutParams.MATCH_PARENT));

        card.addView(track, trackLp);

        card.setOnClickListener(v -> onClick.run());
        LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardLp.bottomMargin = (int) (10 * dp);
        card.setLayoutParams(cardLp);
        return card;
    }

    private static void openPlan(Context ctx, String planId, int totalDays, String name, String reflection) {
        float dp = ctx.getResources().getDisplayMetrics().density;
        int done = SanctuaryStore.getPlanCompletedDays(ctx, planId);
        int nextDay = Math.min(totalDays, done + 1);

        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout root = createSheetContainer(ctx, dp);

        TextView eyebrow = new TextView(ctx);
        eyebrow.setText(ctx.getString(R.string.plan_day_progress_fmt, nextDay, totalDays));
        eyebrow.setTextSize(11.5f);
        eyebrow.setLetterSpacing(0.14f);
        eyebrow.setTypeface(Typeface.DEFAULT_BOLD);
        eyebrow.setTextColor(0xFFE6BC5C);
        root.addView(eyebrow);

        TextView title = new TextView(ctx);
        title.setText(name);
        title.setTextSize(23f);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setTextColor(0xFFF7F3FF);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = (int) (6 * dp);
        root.addView(title, titleLp);

        TextView body = new TextView(ctx);
        body.setText(reflection);
        body.setTextSize(16f);
        body.setTypeface(Typeface.SERIF);
        body.setTextColor(0xFFEAE4F8);
        body.setLineSpacing(0f, 1.45f);
        body.setPadding((int) (16 * dp), (int) (16 * dp), (int) (16 * dp), (int) (16 * dp));
        GradientDrawable bodyBg = new GradientDrawable();
        bodyBg.setColor(0x14FFFFFF);
        bodyBg.setCornerRadius(18 * dp);
        bodyBg.setStroke((int) (1 * dp), 0x26FFFFFF);
        body.setBackground(bodyBg);
        LinearLayout.LayoutParams bodyLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bodyLp.topMargin = (int) (16 * dp);
        bodyLp.bottomMargin = (int) (18 * dp);
        root.addView(body, bodyLp);

        TextView primaryBtn = createGoldPill(ctx, dp, done < totalDays
                ? ctx.getString(R.string.plan_complete_day_fmt, nextDay)
                : ctx.getString(R.string.plan_restart));
        primaryBtn.setOnClickListener(v -> {
            if (done < totalDays) {
                int now = SanctuaryStore.completeNextPlanDay(ctx, planId, totalDays);
                SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
                Toast.makeText(ctx, ctx.getString(R.string.plan_day_completed_toast, now, totalDays), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                SanctuaryStore.resetPlan(ctx, planId);
                dialog.dismiss();
                show(ctx);
            }
        });
        root.addView(primaryBtn, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (48 * dp)));

        TextView btnBack = createGhostPill(ctx, dp, ctx.getString(android.R.string.cancel));
        btnBack.setOnClickListener(v -> dialog.dismiss());
        LinearLayout.LayoutParams backLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (44 * dp));
        backLp.topMargin = (int) (10 * dp);
        root.addView(btnBack, backLp);

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
                new int[]{0xFFF3C068, 0xFFD4AF37});
        bg.setCornerRadius(24 * dp);
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
