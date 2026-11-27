package com.sanctus.christian.prayer.ui.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;

import java.util.Locale;

/**
 * 2027+ Guided Breathing & Instant Peace Sanctuary ("Need Peace Now?" / Mood Comfort).
 * Features a 4s Inhale -> 4s Hold -> 6s Exhale golden breathing halo + Scripture + offline TTS.
 */
public final class PeaceSanctuaryDialog {

    private PeaceSanctuaryDialog() {}

    public static void show(Context ctx, String title, String verse, String prayerText) {
        if (ctx == null) return;
        float dp = ctx.getResources().getDisplayMetrics().density;
        int pad = (int) (24 * dp);

        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(pad, pad, pad, (int) (14 * dp));
        root.setBackgroundColor(0xFF0E1016);

        TextView tvTitle = new TextView(ctx);
        tvTitle.setText(title);
        tvTitle.setTextColor(0xFFE6C27A);
        tvTitle.setTextSize(18f);
        tvTitle.setGravity(Gravity.CENTER);
        root.addView(tvTitle);

        // Golden Breathing Halo Circle
        int haloSize = (int) (116 * dp);
        TextView halo = new TextView(ctx);
        halo.setGravity(Gravity.CENTER);
        halo.setTextColor(0xFF0E1016);
        halo.setTextSize(14f);
        halo.setText(R.string.peace_breathe_in);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(0xFFE6C27A);
        halo.setBackground(gd);
        LinearLayout.LayoutParams haloLp = new LinearLayout.LayoutParams(haloSize, haloSize);
        haloLp.topMargin = (int) (20 * dp);
        haloLp.bottomMargin = (int) (18 * dp);
        root.addView(halo, haloLp);

        TextView tvVerse = new TextView(ctx);
        tvVerse.setText(verse);
        tvVerse.setTextColor(0xFFF3F4F6);
        tvVerse.setTextSize(15f);
        tvVerse.setGravity(Gravity.CENTER);
        tvVerse.setLineSpacing(4f * dp, 1.0f);
        root.addView(tvVerse);

        if (prayerText != null && !prayerText.isEmpty()) {
            TextView tvPrayer = new TextView(ctx);
            tvPrayer.setText(prayerText);
            tvPrayer.setTextColor(0xB3E2E8F0);
            tvPrayer.setTextSize(13.5f);
            tvPrayer.setGravity(Gravity.CENTER);
            tvPrayer.setPadding(0, (int) (12 * dp), 0, 0);
            root.addView(tvPrayer);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean[] alive = {true};
        final TextToSpeech[] ttsHolder = {null};

        final Runnable[] cycle = new Runnable[1];
        cycle[0] = () -> {
            if (!alive[0]) return;
            halo.setText(R.string.peace_breathe_in);
            ObjectAnimator inhale = ObjectAnimator.ofPropertyValuesHolder(halo,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0.78f, 1.12f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.78f, 1.12f));
            inhale.setDuration(4000);
            inhale.setInterpolator(new AccelerateDecelerateInterpolator());
            inhale.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!alive[0]) return;
                    halo.setText(R.string.peace_breathe_hold);
                    handler.postDelayed(() -> {
                        if (!alive[0]) return;
                        halo.setText(R.string.peace_breathe_out);
                        ObjectAnimator exhale = ObjectAnimator.ofPropertyValuesHolder(halo,
                                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.12f, 0.78f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.12f, 0.78f));
                        exhale.setDuration(5500);
                        exhale.setInterpolator(new AccelerateDecelerateInterpolator());
                        exhale.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (alive[0]) handler.postDelayed(cycle[0], 600);
                            }
                        });
                        exhale.start();
                    }, 3500);
                }
            });
            inhale.start();
        };

        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setView(root)
                .setPositiveButton(R.string.peace_listen_audio, null)
                .setNegativeButton(R.string.peace_amen_close, null)
                .create();

        dialog.setOnShowListener(d -> {
            handler.post(cycle[0]);
            SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String speakText = verse + ". " + (prayerText != null ? prayerText : "");
                if (ttsHolder[0] == null) {
                    ttsHolder[0] = new TextToSpeech(ctx.getApplicationContext(), status -> {
                        if (status == TextToSpeech.SUCCESS && alive[0] && ttsHolder[0] != null) {
                            ttsHolder[0].setLanguage(Locale.getDefault());
                            ttsHolder[0].setSpeechRate(0.84f);
                            ttsHolder[0].speak(speakText, TextToSpeech.QUEUE_FLUSH, null, "sanctus_peace");
                        }
                    });
                } else {
                    if (ttsHolder[0].isSpeaking()) {
                        ttsHolder[0].stop();
                    } else {
                        ttsHolder[0].speak(speakText, TextToSpeech.QUEUE_FLUSH, null, "sanctus_peace");
                    }
                }
            });
        });

        dialog.setOnDismissListener(d -> {
            alive[0] = false;
            handler.removeCallbacksAndMessages(null);
            if (ttsHolder[0] != null) {
                try {
                    ttsHolder[0].stop();
                    ttsHolder[0].shutdown();
                } catch (Exception ignored) {
                }
            }
        });

        dialog.show();
    }
}
