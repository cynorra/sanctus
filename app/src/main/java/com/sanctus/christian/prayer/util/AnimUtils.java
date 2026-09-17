package com.sanctus.christian.prayer.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class AnimUtils {

    private static final OvershootInterpolator OVERSHOOT = new OvershootInterpolator(3.5f);

    /** Quick scale bounce, e.g. for a favorite toggle. */
    public static void bounce(View view) {
        view.animate().cancel();
        view.setScaleX(0.7f);
        view.setScaleY(0.7f);
        view.animate()
            .scaleX(1f).scaleY(1f)
            .setDuration(320)
            .setInterpolator(OVERSHOOT)
            .setListener(null)
            .start();
    }

    /** Brief press-down feedback for tappable rows/cards. */
    public static void pressFeedback(View view) {
        view.animate().cancel();
        view.animate()
            .scaleX(0.97f).scaleY(0.97f)
            .setDuration(80)
            .setListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    view.animate().scaleX(1f).scaleY(1f).setDuration(120).setListener(null).start();
                }
            }).start();
    }
}
