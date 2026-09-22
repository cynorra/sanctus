package com.sanctus.christian.prayer.analytics;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Custom conversion events for Google Ads / App Campaigns to optimize against.
 * Firebase already auto-logs first_open, session_start, screen_view — these cover the
 * funnel steps beyond that (onboarding, actual prayer/rosary use, sign-in, invites).
 */
public class AnalyticsLogger {

    public static void logOnboardingComplete(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent("onboarding_complete", null);
    }

    public static void logPrayerCompleted(Context context, String category) {
        Bundle params = new Bundle();
        if (category != null) params.putString("prayer_category", category);
        FirebaseAnalytics.getInstance(context).logEvent("prayer_completed", params);
    }

    public static void logRosaryCompleted(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent("rosary_completed", null);
    }

    public static void logSignIn(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.LOGIN, null);
    }

    public static void logInviteShared(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SHARE, null);
    }
}
