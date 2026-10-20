package com.sanctus.christian.prayer.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Small motion vocabulary shared by every screen: staggered entrances, tactile press scaling, a
 * gold shimmer for key text and a slow "breathing" glow. All of it uses View property animators so
 * it adds no dependencies and respects the system animation-duration scale.
 */
public final class MotionKit {

    private static final long ENTER_DURATION = 460L;
    private static final long STAGGER = 55L;
    private static final int MAX_STAGGERED = 10;

    private MotionKit() {
    }

    /**
     * Fades and lifts the top-level blocks of a screen in one after another. Works on the common
     * fragment roots here: a ScrollView whose child is a vertical LinearLayout, or a plain container.
     */
    public static void enterScreen(View root) {
        if (root == null) return;
        ViewGroup content = null;
        if (root instanceof ScrollView && ((ScrollView) root).getChildCount() > 0
                && ((ScrollView) root).getChildAt(0) instanceof ViewGroup) {
            content = (ViewGroup) ((ScrollView) root).getChildAt(0);
        } else if (root instanceof ViewGroup) {
            content = (ViewGroup) root;
        }
        if (content == null) return;
        float lift = 26f * root.getResources().getDisplayMetrics().density;
        int count = Math.min(content.getChildCount(), MAX_STAGGERED);
        for (int i = 0; i < count; i++) {
            View child = content.getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            enterView(child, i * STAGGER, lift);
        }
    }

    public static void enterView(View view, long delayMs, float liftPx) {
        view.animate().cancel();
        view.setAlpha(0f);
        view.setTranslationY(liftPx);
        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delayMs)
                .setDuration(ENTER_DURATION)
                .setInterpolator(new DecelerateInterpolator(2.2f))
                .setListener(null)
                .start();
    }

    /** Springy scale-down on touch and overshoot back on release. Does not consume the touch. */
    @SuppressLint("ClickableViewAccessibility")
    public static void pressable(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().cancel();
                    v.animate().scaleX(0.965f).scaleY(0.965f).setDuration(110)
                            .setInterpolator(new DecelerateInterpolator()).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().cancel();
                    v.animate().scaleX(1f).scaleY(1f).setDuration(320)
                            .setInterpolator(new OvershootInterpolator(2.4f)).start();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    /** A soft gold highlight that sweeps across the text, repeating until the view detaches. */
    public static void shimmer(TextView text, int baseColor, int highlightColor) {
        text.post(() -> {
            float width = Math.max(text.getWidth(), 1);
            LinearGradient gradient = new LinearGradient(
                    0, 0, width * 0.6f, 0,
                    new int[]{baseColor, highlightColor, baseColor},
                    new float[]{0f, 0.5f, 1f}, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            text.getPaint().setShader(gradient);
            ValueAnimator animator = ValueAnimator.ofFloat(-width * 0.6f, width * 1.2f);
            animator.setDuration(2600);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setStartDelay(900);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(a -> {
                matrix.setTranslate((float) a.getAnimatedValue(), 0);
                gradient.setLocalMatrix(matrix);
                text.invalidate();
            });
            text.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override public void onViewAttachedToWindow(View v) { }

                @Override public void onViewDetachedFromWindow(View v) {
                    animator.cancel();
                    text.getPaint().setShader(null);
                }
            });
            animator.start();
        });
    }

    /** Slow scale/alpha loop, e.g. for a glow behind the cross. Stops when the view detaches. */
    public static void breathe(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(3200);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(a -> {
            float f = (float) a.getAnimatedValue();
            float eased = f * f * (3f - 2f * f);
            view.setScaleX(0.92f + 0.16f * eased);
            view.setScaleY(0.92f + 0.16f * eased);
            view.setAlpha(0.55f + 0.45f * eased);
        });
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override public void onViewAttachedToWindow(View v) { }

            @Override public void onViewDetachedFromWindow(View v) {
                animator.cancel();
            }
        });
        animator.start();
    }

    /** Rotates a chevron or similar indicator with a short ease. */
    public static void rotateTo(View view, float degrees) {
        view.animate().rotation(degrees).setDuration(240)
                .setInterpolator(new DecelerateInterpolator()).start();
    }
}
