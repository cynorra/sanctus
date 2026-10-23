package com.sanctus.christian.prayer.util;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.sanctus.christian.prayer.R;

/**
 * Manages interstitial ad lifecycle according to strict production rules:
 * 1. Only official production ad unit ID (no test IDs).
 * 2. Preloads in the background before user action.
 * 3. Immediately preloads the next ad when current ad is closed or fails to show.
 * 4. Silent failure on error / no-fill (no crash, no intrusive UI).
 */
public class AdManager {

    private static volatile AdManager instance;
    private InterstitialAd interstitialAd;
    private boolean isLoading = false;

    private AdManager() {}

    public static AdManager getInstance() {
        if (instance == null) {
            synchronized (AdManager.class) {
                if (instance == null) {
                    instance = new AdManager();
                }
            }
        }
        return instance;
    }

    /**
     * Preloads an interstitial ad in the background.
     */
    public void preloadInterstitial(@NonNull Context context) {
        if (interstitialAd != null || isLoading) {
            return;
        }

        isLoading = true;
        Context appContext = context.getApplicationContext();
        String adUnitId = appContext.getString(R.string.admob_interstitial_ad_unit_id);
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(appContext, adUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                interstitialAd = ad;
                isLoading = false;

                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialAd = null;
                        // Lifecycle rule: Preload the next interstitial immediately on dismiss
                        preloadInterstitial(appContext);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        interstitialAd = null;
                        // Silent fail and reload
                        preloadInterstitial(appContext);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Silent fail on load failure / no-fill (e.g. error code 3)
                interstitialAd = null;
                isLoading = false;
            }
        });
    }

    /**
     * Shows the loaded interstitial ad if available.
     * Silent fail if not ready.
     */
    public void showInterstitial(@NonNull Activity activity) {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        } else {
            // Not ready yet, silently trigger a preload for future actions
            preloadInterstitial(activity);
        }
    }
}
