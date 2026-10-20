package com.sanctus.christian.prayer.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.util.MotionKit;
import com.sanctus.christian.prayer.util.Tradition;

/**
 * First-run flow: welcome, pick a tradition, daily rhythm. It also runs once for people updating
 * from a version that had no tradition setting, so everyone ends up with a deliberate choice.
 */
public class OnboardingActivity extends AppCompatActivity {

    private static final int STEP_COUNT = 3;
    private static final int STEP_TRADITION = 1;

    private View[] steps;
    private View[] dots;
    private MaterialButton btnContinue;
    private int step = 0;
    private String selectedTradition = null;
    private final View[] traditionCards = new View[Tradition.CHOICES.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        steps = new View[]{
                findViewById(R.id.step_welcome),
                findViewById(R.id.step_tradition),
                findViewById(R.id.step_rhythm)};
        btnContinue = findViewById(R.id.btn_continue);

        buildDots();
        buildTraditionCards();

        MotionKit.breathe(findViewById(R.id.glow_cross));
        MotionKit.breathe(findViewById(R.id.glow_rhythm));
        MotionKit.shimmer((TextView) findViewById(R.id.tv_welcome_title),
                ContextCompat.getColor(this, R.color.color_on_background),
                ContextCompat.getColor(this, R.color.color_gold_light));
        MotionKit.pressable(btnContinue);

        btnContinue.setOnClickListener(v -> next());
        findViewById(R.id.tv_skip).setOnClickListener(v -> finish(Tradition.GENERAL));

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (step > 0) {
                    show(step - 1, false);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        showFirst();
    }

    private void buildDots() {
        LinearLayout row = findViewById(R.id.dots);
        dots = new View[STEP_COUNT];
        int size = dp(8);
        for (int i = 0; i < STEP_COUNT; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMarginStart(i == 0 ? 0 : dp(8));
            dot.setLayoutParams(lp);
            dot.setBackgroundResource(R.drawable.bg_gold_pill);
            row.addView(dot);
            dots[i] = dot;
        }
    }

    private void buildTraditionCards() {
        ViewGroup list = findViewById(R.id.list_traditions);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < Tradition.CHOICES.length; i++) {
            final String id = Tradition.CHOICES[i];
            View card = inflater.inflate(R.layout.item_tradition_card, list, false);
            ((TextView) card.findViewById(R.id.tv_tradition_name)).setText(Tradition.labelRes(id));
            ((TextView) card.findViewById(R.id.tv_tradition_desc)).setText(Tradition.descriptionRes(id));
            MotionKit.pressable(card);
            card.setOnClickListener(v -> select(id));
            list.addView(card);
            traditionCards[i] = card;
        }
    }

    private void select(String id) {
        selectedTradition = id;
        for (int i = 0; i < Tradition.CHOICES.length; i++) {
            boolean on = Tradition.CHOICES[i].equals(id);
            traditionCards[i].setSelected(on);
            traditionCards[i].findViewById(R.id.radio_dot).setSelected(on);
        }
        updateButton();
    }

    private void showFirst() {
        for (int i = 0; i < steps.length; i++) {
            steps[i].setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        }
        MotionKit.enterScreen(steps[0]);
        refreshChrome();
    }

    private void next() {
        if (step < STEP_COUNT - 1) {
            show(step + 1, true);
        } else {
            finish(selectedTradition != null ? selectedTradition : Tradition.GENERAL);
        }
    }

    /** Crossfades to another step, sliding in from the side it logically comes from. */
    private void show(int target, boolean forward) {
        if (target == step) return;
        final View out = steps[step];
        final View in = steps[target];
        float shift = dp(36) * (forward ? 1 : -1);

        out.animate().alpha(0f).translationX(-shift).setDuration(180)
                .setInterpolator(new DecelerateInterpolator()).withEndAction(() -> {
                    out.setVisibility(View.GONE);
                    out.setAlpha(1f);
                    out.setTranslationX(0f);
                }).start();

        in.setAlpha(0f);
        in.setTranslationX(shift);
        in.setVisibility(View.VISIBLE);
        in.animate().alpha(1f).translationX(0f).setStartDelay(120).setDuration(340)
                .setInterpolator(new DecelerateInterpolator(2f)).start();

        step = target;
        refreshChrome();
    }

    private void refreshChrome() {
        for (int i = 0; i < dots.length; i++) {
            boolean active = i == step;
            dots[i].animate().alpha(active ? 1f : 0.28f).scaleX(active ? 1.4f : 1f)
                    .scaleY(active ? 1.4f : 1f).setDuration(220).start();
        }
        btnContinue.setText(step == STEP_COUNT - 1 ? R.string.onboarding_begin : R.string.onboarding_continue);
        findViewById(R.id.tv_skip).setVisibility(step == STEP_COUNT - 1 ? View.INVISIBLE : View.VISIBLE);
        updateButton();
    }

    /** The tradition step needs a choice before moving on; Skip covers people who don't want one. */
    private void updateButton() {
        boolean needsChoice = step == STEP_TRADITION && selectedTradition == null;
        btnContinue.setEnabled(!needsChoice);
        btnContinue.animate().alpha(needsChoice ? 0.4f : 1f).setDuration(160).start();
    }

    private void finish(String tradition) {
        SettingsRepository settings = SanctusApplication.getInstance().getSettingsRepository();
        if (settings != null) {
            settings.setTradition(tradition);
            settings.setOnboardingDone(true);
        }
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
