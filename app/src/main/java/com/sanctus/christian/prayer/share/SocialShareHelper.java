package com.sanctus.christian.prayer.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/** Fires a package-targeted ACTION_SEND at one specific social app, image + caption text. */
public class SocialShareHelper {

    public enum Platform {
        WHATSAPP("com.whatsapp"),
        FACEBOOK("com.facebook.katana"),
        INSTAGRAM("com.instagram.android"),
        TIKTOK("com.zhiliaoapp.musically", "com.ss.android.ugc.trill");

        final String[] packageNames;
        Platform(String... packageNames) { this.packageNames = packageNames; }
    }

    public static boolean isInstalled(Context context, Platform platform) {
        PackageManager pm = context.getPackageManager();
        for (String pkg : platform.packageNames) {
            if (pm.getLaunchIntentForPackage(pkg) != null) return true;
        }
        return false;
    }

    /** @return true if an installed app handled the share, false if the platform isn't installed. */
    public static boolean share(Context context, Platform platform, Uri imageUri, String text) {
        PackageManager pm = context.getPackageManager();
        for (String pkg : platform.packageNames) {
            if (pm.getLaunchIntentForPackage(pkg) == null) continue;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setPackage(pkg);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
                return true;
            } catch (Exception ignored) {
                // Try the next package variant (e.g. TikTok's alternate package name) if any.
            }
        }
        return false;
    }
}
