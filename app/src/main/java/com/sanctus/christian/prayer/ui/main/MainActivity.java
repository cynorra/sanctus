package com.sanctus.christian.prayer.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.ui.prayer.PrayerDetailFragment;
import com.sanctus.christian.prayer.util.MotionKit;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DEEP_LINK_PRAYER_NAME = "deep_link_prayer_name";
    public static final String EXTRA_DEEP_LINK_PRAYER_LANGUAGE = "deep_link_prayer_language";

    private NavController navController;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Modern Edge-to-Edge support (Android 15 / 16 compliance)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        View contentView = findViewById(android.R.id.content);
        if (contentView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(contentView, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                return WindowInsetsCompat.CONSUMED;
            });
        }

        // Navigation setup
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            NavigationUI.setupWithNavController(bottomNav, navController);

            // Same behaviour as NavigationUI's default tab handling (single top, restore state),
            // plus a fade/lift transition between tabs that the default does not give us.
            bottomNav.setOnItemSelectedListener(item -> {
                NavDestination current = navController.getCurrentDestination();
                if (current != null && current.getId() == item.getItemId()) return true;
                NavOptions options = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setRestoreState(true)
                        .setPopUpTo(navController.getGraph().getStartDestinationId(), false, true)
                        .setEnterAnim(R.anim.tab_enter)
                        .setExitAnim(R.anim.tab_exit)
                        .setPopEnterAnim(R.anim.tab_enter)
                        .setPopExitAnim(R.anim.tab_exit)
                        .build();
                try {
                    navController.navigate(item.getItemId(), null, options);
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            });

            // Every screen gets the same staggered entrance without each fragment wiring it up.
            navHostFragment.getChildFragmentManager().registerFragmentLifecycleCallbacks(
                    new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                                          @NonNull View v, Bundle savedInstanceState) {
                            MotionKit.enterScreen(v);
                        }
                    }, false);
        }

        handleDeepLink(getIntent());

        // Load AdMob Banner
        adView = findViewById(R.id.adView);
        if (adView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleDeepLink(intent);
    }

    /**
     * The daily engagement push links straight to the specific prayer it mentioned, not just the
     * app's default screen (see SanctusFirebaseMessagingService for why this needs a data-only
     * FCM payload to work at all). The push only carries the prayer's name/language since that's
     * all Firestore's copy has — the local Room row id is looked up here, off the main thread.
     */
    private void handleDeepLink(Intent intent) {
        if (intent == null || navController == null) return;
        String name = intent.getStringExtra(EXTRA_DEEP_LINK_PRAYER_NAME);
        String language = intent.getStringExtra(EXTRA_DEEP_LINK_PRAYER_LANGUAGE);
        if (name == null || language == null) return;

        // Consume so a config change / re-entry into this activity doesn't re-navigate.
        intent.removeExtra(EXTRA_DEEP_LINK_PRAYER_NAME);
        intent.removeExtra(EXTRA_DEEP_LINK_PRAYER_LANGUAGE);

        Executors.newSingleThreadExecutor().execute(() -> {
            PrayerEntity prayer = SanctusDatabase.getInstance(this).prayerDao()
                    .findByNameAndLanguage(name, language);
            if (prayer == null) return;
            runOnUiThread(() -> {
                Bundle args = new Bundle();
                args.putInt(PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
                navController.navigate(R.id.prayerDetailFragment, args);
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return (navController != null && navController.navigateUp()) || super.onSupportNavigateUp();
    }
}
