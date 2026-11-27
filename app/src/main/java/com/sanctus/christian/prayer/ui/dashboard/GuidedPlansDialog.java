package com.sanctus.christian.prayer.ui.dashboard;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.SanctuaryStore;

/**
 * Multi-Day Guided Devotional Plans (7-Day Peace, 7-Day Psalms of Gratitude, 9-Day Novena)
 * with daily check-off progress tracking.
 */
public final class GuidedPlansDialog {

    private GuidedPlansDialog() {}

    public static void show(Context ctx) {
        if (ctx == null) return;
        int p1 = SanctuaryStore.getPlanCompletedDays(ctx, "peace_7");
        int p2 = SanctuaryStore.getPlanCompletedDays(ctx, "gratitude_7");
        int p3 = SanctuaryStore.getPlanCompletedDays(ctx, "novena_9");

        String[] items = {
                ctx.getString(R.string.plan_peace_title_fmt, p1, 7),
                ctx.getString(R.string.plan_gratitude_title_fmt, p2, 7),
                ctx.getString(R.string.plan_novena_title_fmt, p3, 9)
        };

        new AlertDialog.Builder(ctx)
                .setTitle(R.string.plans_dialog_title)
                .setItems(items, (d, which) -> {
                    if (which == 0) {
                        openPlan(ctx, "peace_7", 7,
                                ctx.getString(R.string.plan_peace_name),
                                ctx.getString(R.string.plan_peace_reflection));
                    } else if (which == 1) {
                        openPlan(ctx, "gratitude_7", 7,
                                ctx.getString(R.string.plan_gratitude_name),
                                ctx.getString(R.string.plan_gratitude_reflection));
                    } else {
                        openPlan(ctx, "novena_9", 9,
                                ctx.getString(R.string.plan_novena_name),
                                ctx.getString(R.string.plan_novena_reflection));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private static void openPlan(Context ctx, String planId, int totalDays, String name, String reflection) {
        int done = SanctuaryStore.getPlanCompletedDays(ctx, planId);
        int nextDay = Math.min(totalDays, done + 1);
        String msg = ctx.getString(R.string.plan_day_progress_fmt, nextDay, totalDays) + "\n\n" + reflection;

        AlertDialog.Builder b = new AlertDialog.Builder(ctx)
                .setTitle(name)
                .setMessage(msg)
                .setNegativeButton(android.R.string.cancel, null);

        if (done < totalDays) {
            b.setPositiveButton(ctx.getString(R.string.plan_complete_day_fmt, nextDay), (d, w) -> {
                int now = SanctuaryStore.completeNextPlanDay(ctx, planId, totalDays);
                SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
                Toast.makeText(ctx, ctx.getString(R.string.plan_day_completed_toast, now, totalDays), Toast.LENGTH_SHORT).show();
            });
        } else {
            b.setPositiveButton(R.string.plan_restart, (d, w) -> {
                SanctuaryStore.resetPlan(ctx, planId);
                show(ctx);
            });
        }
        b.show();
    }
}
