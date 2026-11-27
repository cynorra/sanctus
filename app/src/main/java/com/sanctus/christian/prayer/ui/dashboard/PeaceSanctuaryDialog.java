package com.sanctus.christian.prayer.ui.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.MotionKit;

import java.util.Locale;

/**
 * 2027+ Luxury Glass Breathing & Instant Peace Sanctuary.
 * Custom frameless rounded dark-gold container with dual concentric breathing halos + offline TTS.
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
        root.setPadding(pad, (int) (26 * dp), pad, (int) (22 * dp));

        GradientDrawable sheetBg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{0xFF171B28, 0xFF07090E, 0xFF131622});
        sheetBg.setCornerRadius(28f * dp);
        sheetBg.setStroke((int) (1.2f * dp), 0x44D4AF37);
        root.setBackground(sheetBg);

        TextView tvTitle = new TextView(ctx);
        tvTitle.setText(title);
        tvTitle.setTextColor(0xFFF3C068);
        tvTitle.setTextSize(20f);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setLetterSpacing(0.03f);
        root.addView(tvTitle);

        // Dual Concentric Breathing Halo
        int boxSize = (int) (152 * dp);
        FrameLayout haloBox = new FrameLayout(ctx);
        LinearLayout.LayoutParams boxLp = new LinearLayout.LayoutParams(boxSize, boxSize);
        boxLp.topMargin = (int) (18 * dp);
        boxLp.bottomMargin = (int) (16 * dp);
        root.addView(haloBox, boxLp);

        View outerRing = new View(ctx);
        GradientDrawable outerGd = new GradientDrawable();
        outerGd.setShape(GradientDrawable.OVAL);
        outerGd.setColor(0x22F3C068);
        outerGd.setStroke((int) (1.2f * dp), 0x55D4AF37);
        outerRing.setBackground(outerGd);
        FrameLayout.LayoutParams outerLp = new FrameLayout.LayoutParams((int) (136 * dp), (int) (136 * dp), Gravity.CENTER);
        haloBox.addView(outerRing, outerLp);

        TextView halo = new TextView(ctx);
        halo.setGravity(Gravity.CENTER);
        halo.setTextColor(0xFF07090E);
        halo.setTextSize(13.5f);
        halo.setText(R.string.peace_breathe_in);
        GradientDrawable innerGd = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{0xFFF3C068, 0xFFD4AF37});
        innerGd.setShape(GradientDrawable.OVAL);
        halo.setBackground(innerGd);
        FrameLayout.LayoutParams innerLp = new FrameLayout.LayoutParams((int) (104 * dp), (int) (104 * dp), Gravity.CENTER);
        haloBox.addView(halo, innerLp);

        TextView tvVerse = new TextView(ctx);
        tvVerse.setText(verse);
        tvVerse.setTextColor(0xFFF5F2EB);
        tvVerse.setTextSize(15f);
        tvVerse.setGravity(Gravity.CENTER);
        tvVerse.setLineSpacing(4f * dp, 1.0f);
        root.addView(tvVerse);

        if (prayerText != null && !prayerText.isEmpty()) {
            TextView tvPrayer = new TextView(ctx);
            tvPrayer.setText(prayerText);
            tvPrayer.setTextColor(0xFF9E988D);
            tvPrayer.setTextSize(13.5f);
            tvPrayer.setGravity(Gravity.CENTER);
            tvPrayer.setPadding(0, (int) (12 * dp), 0, 0);
            root.addView(tvPrayer);
        }

        // Custom Luxury Action Pill Row
        LinearLayout actions = new LinearLayout(ctx);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams actLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        actLp.topMargin = (int) (22 * dp);
        root.addView(actions, actLp);

        TextView btnListen = new TextView(ctx);
        btnListen.setText(R.string.peace_listen_audio);
        btnListen.setGravity(Gravity.CENTER);
        btnListen.setTextSize(13.5f);
        btnListen.setTextColor(0xFF07090E);
        GradientDrawable listenBg = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFFF3C068, 0xFFD4AF37});
        listenBg.setCornerRadius(22f * dp);
        btnListen.setBackground(listenBg);
        LinearLayout.LayoutParams btnLp1 = new LinearLayout.LayoutParams(0, (int) (44 * dp), 1f);
        btnLp1.setMarginEnd((int) (8 * dp));
        actions.addView(btnListen, btnLp1);
        MotionKit.pressable(btnListen);

        TextView btnAmen = new TextView(ctx);
        btnAmen.setText(R.string.peace_amen_close);
        btnAmen.setGravity(Gravity.CENTER);
        btnAmen.setTextSize(13.5f);
        btnAmen.setTextColor(0xFFF5F2EB);
        GradientDrawable amenBg = new GradientDrawable();
        amenBg.setColor(0xFF11141E);
        amenBg.setStroke((int) dp, 0x20FFFFFF);
        amenBg.setCornerRadius(22f * dp);
        btnAmen.setBackground(amenBg);
        LinearLayout.LayoutParams btnLp2 = new LinearLayout.LayoutParams(0, (int) (44 * dp), 1f);
        actions.addView(btnAmen, btnLp2);
        MotionKit.pressable(btnAmen);

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean[] alive = {true};
        final TextToSpeech[] ttsHolder = {null};

        final Runnable[] cycle = new Runnable[1];
        cycle[0] = () -> {
            if (!alive[0]) return;
            halo.setText(R.string.peace_breathe_in);
            ObjectAnimator inInner = ObjectAnimator.ofPropertyValuesHolder(halo,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0.80f, 1.10f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.80f, 1.10f));
            ObjectAnimator inOuter = ObjectAnimator.ofPropertyValuesHolder(outerRing,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0.78f, 1.14f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.78f, 1.14f),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 0.35f, 0.95f));
            inInner.setDuration(4000);
            inOuter.setDuration(4000);
            inInner.setInterpolator(new AccelerateDecelerateInterpolator());
            inOuter.setInterpolator(new AccelerateDecelerateInterpolator());
            inInner.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!alive[0]) return;
                    halo.setText(R.string.peace_breathe_hold);
                    handler.postDelayed(() -> {
                        if (!alive[0]) return;
                        halo.setText(R.string.peace_breathe_out);
                        ObjectAnimator outInner = ObjectAnimator.ofPropertyValuesHolder(halo,
                                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.10f, 0.80f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.10f, 0.80f));
                        ObjectAnimator outOuter = ObjectAnimator.ofPropertyValuesHolder(outerRing,
                                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.14f, 0.78f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.14f, 0.78f),
                                PropertyValuesHolder.ofFloat(View.ALPHA, 0.95f, 0.35f));
                        outInner.setDuration(5500);
                        outOuter.setDuration(5500);
                        outInner.setInterpolator(new AccelerateDecelerateInterpolator());
                        outOuter.setInterpolator(new AccelerateDecelerateInterpolator());
                        outInner.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (alive[0]) handler.postDelayed(cycle[0], 500);
                            }
                        });
                        outInner.start();
                        outOuter.start();
                    }, 3500);
                }
            });
            inInner.start();
            inOuter.start();
        };

        AlertDialog dialog = new AlertDialog.Builder(ctx).setView(root).create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btnAmen.setOnClickListener(v -> dialog.dismiss());
        btnListen.setOnClickListener(v -> {
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

        dialog.setOnShowListener(d -> {
            handler.post(cycle[0]);
            SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
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
