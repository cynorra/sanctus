package com.sanctus.christian.prayer.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.ui.onboarding.OnboardingActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Check if onboarding was shown before
            boolean onboardingDone = SanctusApplication.getInstance()
                    .getSettingsRepository().isNotificationsEnabled();
            // Use a dedicated pref for onboarding shown flag
            boolean shown = getSharedPreferences("sanctus_prefs", MODE_PRIVATE)
                    .getBoolean("onboarding_shown", false);

            if (!shown) {
                // Mark onboarding shown
                getSharedPreferences("sanctus_prefs", MODE_PRIVATE)
                        .edit().putBoolean("onboarding_shown", true).apply();
                startActivity(new Intent(this, OnboardingActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        }, SPLASH_DELAY);
    }
}
