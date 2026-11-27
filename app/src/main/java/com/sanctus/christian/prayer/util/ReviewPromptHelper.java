package com.sanctus.christian.prayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;

import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.sanctus.christian.prayer.R;

/** Play-policy-compliant In-App Review prompt (2nd launch, 6s delay, 21-day cooldown, max 2). */
public final class ReviewPromptHelper {

    private static final String PREFS = "sanctus_review_prompt";
    private static final long COOLDOWN_MS = 21L * 24 * 60 * 60 * 1000L;

    private ReviewPromptHelper() {}

    public static void trackLaunchAndMaybePrompt(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        SharedPreferences sp = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (sp.getBoolean("forever", false)) return;

        int launches = sp.getInt("launches", 0) + 1;
        sp.edit().putInt("launches", launches).apply();

        int shown = sp.getInt("shown", 0);
        long last = sp.getLong("last", 0L);
        if (launches < 2 || shown >= 2) return;
        if (last > 0 && (System.currentTimeMillis() - last) < COOLDOWN_MS) return;

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (activity.isFinishing() || activity.isDestroyed()) return;
            sp.edit().putInt("shown", shown + 1).putLong("last", System.currentTimeMillis()).apply();
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.review_title)
                    .setMessage(R.string.review_message)
                    .setPositiveButton(R.string.review_rate, (d, w) -> {
                        sp.edit().putBoolean("forever", true).apply();
                        ReviewManager mgr = ReviewManagerFactory.create(activity);
                        mgr.requestReviewFlow().addOnCompleteListener(t -> {
                            if (t.isSuccessful() && !activity.isFinishing()) {
                                mgr.launchReviewFlow(activity, t.getResult());
                            }
                        });
                    })
                    .setNeutralButton(R.string.review_later, null)
                    .setNegativeButton(R.string.review_never, (d, w) ->
                            sp.edit().putBoolean("forever", true).apply())
                    .show();
        }, 6000L);
    }
}
