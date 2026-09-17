package com.sanctus.christian.prayer.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.sanctus.christian.prayer.BuildConfig;

/** Fires a package-targeted ACTION_SEND (or a platform's dedicated Story intent) at one social app. */
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

    /** Normal feed/chat share: image + caption text via ACTION_SEND. */
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

    /**
     * Instagram's dedicated Stories composer — opens straight into the Stories editor with the
     * image as the background, instead of the generic share sheet. Falls back to a normal share
     * if Instagram's Stories intent doesn't resolve (e.g. an old Instagram version).
     */
    public static boolean shareToInstagramStory(Context context, Uri imageUri) {
        if (!isInstalled(context, Platform.INSTAGRAM)) return false;
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("source_application", BuildConfig.APPLICATION_ID);
        intent.setPackage("com.instagram.android");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return share(context, Platform.INSTAGRAM, imageUri, "");
        }
    }

    /**
     * Facebook's dedicated Stories composer. Requires a registered Facebook App ID to be fully
     * reliable — this app doesn't have one registered, so this can silently fail to resolve on
     * some Facebook versions; callers should fall back to a normal share if it returns false.
     */
    public static boolean shareToFacebookStory(Context context, Uri imageUri) {
        if (!isInstalled(context, Platform.FACEBOOK)) return false;
        Intent intent = new Intent("com.facebook.stories.ADD_TO_STORY");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", BuildConfig.APPLICATION_ID);
        intent.setPackage("com.facebook.katana");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return share(context, Platform.FACEBOOK, imageUri, "");
        }
    }
}
