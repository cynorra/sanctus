package com.sanctus.christian.prayer.share;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;

/** One place that builds share captions and opens the share sheet, so every card shares identically. */
public final class ShareActions {
    private static final String TAG = "share_sheet";

    private ShareActions() {}

    public static void sharePrayer(Context ctx, FragmentManager fm, PrayerEntity prayer) {
        String text = ctx.getString(R.string.share_prayer_text, prayer.name, prayer.content, BuildConfig.PLAY_STORE_URL);
        ShareSheetDialogFragment.create(prayer.name, prayer.content, text, ctx.getString(R.string.share_prayer_chooser_title))
                .show(fm, TAG);
    }

    public static void shareSaint(Context ctx, FragmentManager fm, SaintEntity saint) {
        String text = ctx.getString(R.string.share_saint_text, saint.name,
                ctx.getString(R.string.saint_feast_prefix, saint.feastDate), saint.description, BuildConfig.PLAY_STORE_URL);
        ShareSheetDialogFragment.create(saint.name, saint.description, text, ctx.getString(R.string.share_saint_chooser_title))
                .show(fm, TAG);
    }

    public static void shareIntention(Context ctx, FragmentManager fm, String intention) {
        String text = ctx.getString(R.string.feed_share_post_text, intention, BuildConfig.PLAY_STORE_URL);
        ShareSheetDialogFragment.create(ctx.getString(R.string.feed_compose_title), intention, text, ctx.getString(R.string.share_action))
                .show(fm, TAG);
    }
}
