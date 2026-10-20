package com.sanctus.christian.prayer.ui.rosary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;

/**
 * The rosary drawn as a ring of 60 beads (five decades of: one Our Father bead, ten Hail Marys, one
 * Glory Be). Prayed beads are gold, upcoming ones are faint glass, and a glowing marker glides from
 * bead to bead when the position changes.
 */
public class RosaryRingView extends View {

    private static final int DECADES = 5;
    private static final int PER_DECADE = 12;
    private static final int SLOTS = DECADES * PER_DECADE;

    private static final int GOLD = 0xFFE6BC5C;
    private static final int GOLD_LIGHT = 0xFFF5E3B3;
    private static final int FUTURE_FILL = 0x26FFFFFF;
    private static final int FUTURE_STROKE = 0x40FFFFFF;
    private static final int RING = 0x1FFFFFFF;

    private final Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint beadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int currentSlot = 0;
    private boolean finished = false;
    /** Fractional slot the marker is drawn at; trails currentSlot while it animates. */
    private float markerSlot = 0f;
    private float pulse = 0f;
    private ValueAnimator travel;
    private ValueAnimator pulseAnimator;
    private final float density;

    public RosaryRingView(Context context) {
        this(context, null);
    }

    public RosaryRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(1.5f * density);
        ringPaint.setColor(RING);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1f * density);
        strokePaint.setColor(FUTURE_STROKE);
        beadPaint.setStyle(Paint.Style.FILL);
    }

    /** Decade 0-4 and bead 0-11 as tracked by the view model; decade 5 means the rosary is complete. */
    public void setPosition(int decade, int bead, boolean isFinished) {
        int slot = isFinished ? SLOTS : Math.min(decade * PER_DECADE + bead, SLOTS - 1);
        boolean wasFinished = finished;
        finished = isFinished;
        if (slot == currentSlot && wasFinished == isFinished) return;
        float from = markerSlot;
        currentSlot = slot;
        if (travel != null) travel.cancel();
        float target = Math.min(slot, SLOTS - 1);
        if (!isLaidOut()) {
            markerSlot = target;
            invalidate();
            return;
        }
        travel = ValueAnimator.ofFloat(from, target);
        travel.setDuration(380);
        travel.setInterpolator(new DecelerateInterpolator(2f));
        travel.addUpdateListener(a -> {
            markerSlot = (float) a.getAnimatedValue();
            invalidate();
        });
        travel.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pulseAnimator = ValueAnimator.ofFloat(0f, 1f);
        pulseAnimator.setDuration(1600);
        pulseAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setInterpolator(new LinearInterpolator());
        pulseAnimator.addUpdateListener(a -> {
            pulse = (float) a.getAnimatedValue();
            invalidate();
        });
        pulseAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (pulseAnimator != null) pulseAnimator.cancel();
        if (travel != null) travel.cancel();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) / 2f - 16f * density;
        if (radius <= 0) return;

        canvas.drawCircle(cx, cy, radius, ringPaint);

        for (int i = 0; i < SLOTS; i++) {
            float size = beadRadius(i % PER_DECADE);
            float[] p = position(cx, cy, radius, i);
            boolean prayed = finished || i < currentSlot;
            if (prayed) {
                beadPaint.setColor(GOLD);
                canvas.drawCircle(p[0], p[1], size, beadPaint);
            } else {
                beadPaint.setColor(FUTURE_FILL);
                canvas.drawCircle(p[0], p[1], size, beadPaint);
                canvas.drawCircle(p[0], p[1], size, strokePaint);
            }
        }

        if (!finished) {
            float[] m = interpolatedPosition(cx, cy, radius, markerSlot);
            float glowRadius = (16f + 6f * pulse) * density;
            glowPaint.setShader(new RadialGradient(m[0], m[1], glowRadius,
                    new int[]{0x99E6BC5C, 0x00E6BC5C}, null, Shader.TileMode.CLAMP));
            canvas.drawCircle(m[0], m[1], glowRadius, glowPaint);
            beadPaint.setColor(GOLD_LIGHT);
            canvas.drawCircle(m[0], m[1], (7f + 1.2f * pulse) * density, beadPaint);
        }
    }

    /** Our Father beads are the largest, Glory Be a step up from the Hail Mary beads. */
    private float beadRadius(int indexInDecade) {
        if (indexInDecade == 0) return 6.5f * density;
        if (indexInDecade == PER_DECADE - 1) return 4.8f * density;
        return 3.4f * density;
    }

    private float[] position(float cx, float cy, float radius, int slot) {
        double angle = Math.toRadians(-90.0 + slot * 360.0 / SLOTS);
        return new float[]{(float) (cx + radius * Math.cos(angle)), (float) (cy + radius * Math.sin(angle))};
    }

    private float[] interpolatedPosition(float cx, float cy, float radius, float slot) {
        double angle = Math.toRadians(-90.0 + slot * 360.0 / SLOTS);
        return new float[]{(float) (cx + radius * Math.cos(angle)), (float) (cy + radius * Math.sin(angle))};
    }
}
