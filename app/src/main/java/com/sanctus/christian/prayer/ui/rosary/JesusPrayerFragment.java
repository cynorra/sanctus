package com.sanctus.christian.prayer.ui.rosary;

import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.MotionKit;

/**
 * The Orthodox prayer rope: a quiet counter for the Jesus Prayer. One tap is one prayer, the ring
 * fills to 100, and every 33rd knot gives a firmer haptic, as on a 33-knot komboskini.
 */
public class JesusPrayerFragment extends Fragment {

    private static final int TOTAL = 100;
    private static final String STATE_COUNT = "count";

    private int count;
    private CircularProgressIndicator ring;
    private TextView tvCount;
    private View btnAgain;
    private View touch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jesus_prayer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) count = savedInstanceState.getInt(STATE_COUNT, 0);

        ring = view.findViewById(R.id.jesus_ring);
        tvCount = view.findViewById(R.id.jesus_count);
        btnAgain = view.findViewById(R.id.jesus_again);
        touch = view.findViewById(R.id.jesus_touch);
        TextView tvText = view.findViewById(R.id.jesus_text);
        TextView tvTotal = view.findViewById(R.id.jesus_total);

        tvText.setTextSize(SanctusApplication.getInstance().getSettingsRepository().getTextSize() + 5);
        tvTotal.setText(getString(R.string.jesus_prayer_of_total, TOTAL));
        MotionKit.breathe(view.findViewById(R.id.jesus_glow));
        MotionKit.pressable(btnAgain);

        touch.setOnClickListener(v -> {
            if (count >= TOTAL) return;
            count++;
            boolean knot = count % 33 == 0 || count == TOTAL;
            int haptic = knot
                    ? (Build.VERSION.SDK_INT >= 30 ? HapticFeedbackConstants.CONFIRM : HapticFeedbackConstants.LONG_PRESS)
                    : HapticFeedbackConstants.CONTEXT_CLICK;
            v.performHapticFeedback(haptic);
            pulse();
            render(true);
            if (count == TOTAL) {
                SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
            }
        });

        btnAgain.setOnClickListener(v -> {
            count = 0;
            render(true);
        });

        render(false);
    }

    private void pulse() {
        touch.animate().cancel();
        touch.setScaleX(0.97f);
        touch.setScaleY(0.97f);
        touch.animate().scaleX(1f).scaleY(1f).setDuration(220).start();
    }

    private void render(boolean animate) {
        ring.setProgressCompat(count, animate);
        tvCount.setText(String.valueOf(count));
        boolean done = count >= TOTAL;
        btnAgain.setVisibility(done ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNT, count);
    }
}
