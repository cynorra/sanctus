package com.sanctus.christian.prayer.referral;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Captures the referral code from Google Play's Install Referrer API on first launch — no
 * App Links / custom domain needed, and the API itself is free. The code we embed in shared
 * links is read back here and stored locally until the user actually signs in, at which point
 * UserRepository attributes the new account to whoever invited them.
 */
public class InstallReferrerHelper {
    private static final String TAG = "InstallReferrerHelper";
    private static final String PREFS = "sanctus_prefs";
    private static final String KEY_CHECKED = "referrer_checked";
    private static final String KEY_PENDING_CODE = "pending_referral_code";

    public static void captureOnce(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (prefs.getBoolean(KEY_CHECKED, false)) return;

        InstallReferrerClient client = InstallReferrerClient.newBuilder(context.getApplicationContext()).build();
        client.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                try {
                    if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                        ReferrerDetails details = client.getInstallReferrer();
                        String code = extractReferralCode(details.getInstallReferrer());
                        if (code != null) {
                            prefs.edit().putString(KEY_PENDING_CODE, code).apply();
                        }
                    }
                } catch (Exception e) {
                    Log.w(TAG, "Could not read install referrer", e);
                } finally {
                    prefs.edit().putBoolean(KEY_CHECKED, true).apply();
                    client.endConnection();
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // No-op — will simply retry on next app start since KEY_CHECKED wasn't set.
            }
        });
    }

    /** The stored code (if any), for UserRepository to attribute on account creation. */
    public static String consumePendingCode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PENDING_CODE, null);
    }

    private static String extractReferralCode(String rawReferrer) {
        if (rawReferrer == null || rawReferrer.isEmpty()) return null;
        for (String pair : rawReferrer.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) continue;
            String key = pair.substring(0, eq);
            if ("ref_code".equals(key)) {
                try {
                    return URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    return pair.substring(eq + 1);
                }
            }
        }
        return null;
    }
}
