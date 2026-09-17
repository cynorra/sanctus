package com.sanctus.christian.prayer.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.ui.onboarding.OnboardingActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1800;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable navigateRunnable = this::navigateNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        playEntranceAnimation();
        handler.postDelayed(navigateRunnable, SPLASH_DELAY);
    }

    private void playEntranceAnimation() {
        View cross = findViewById(R.id.iv_splash_cross);
        View title = findViewById(R.id.tv_splash_title);
        View tagline = findViewById(R.id.tv_splash_tagline);

        cross.setAlpha(0f);
        cross.setScaleX(0.7f);
        cross.setScaleY(0.7f);
        cross.animate().alpha(1f).scaleX(1f).scaleY(1f).setStartDelay(80).setDuration(420).start();

        title.setAlpha(0f);
        title.setTranslationY(20f);
        title.animate().alpha(1f).translationY(0f).setStartDelay(220).setDuration(420).start();

        tagline.setAlpha(0f);
        tagline.animate().alpha(1f).setStartDelay(420).setDuration(420).start();
    }

    private void navigateNext() {
        boolean onboardingShown = getSharedPreferences("sanctus_prefs", MODE_PRIVATE)
                .getBoolean("onboarding_shown", false);

        if (!onboardingShown) {
            getSharedPreferences("sanctus_prefs", MODE_PRIVATE)
                    .edit().putBoolean("onboarding_shown", true).apply();
            startActivity(new Intent(this, OnboardingActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(navigateRunnable);
        super.onDestroy();
    }
}
