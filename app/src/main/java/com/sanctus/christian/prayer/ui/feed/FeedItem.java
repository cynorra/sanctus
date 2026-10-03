package com.sanctus.christian.prayer.ui.feed;

import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.remote.CommunityPost;

/** One row of the feed list; exactly the field matching {@link #type} is set. */
class FeedItem {
    static final int HEADER = 0, DAILY_PRAYER = 1, DAILY_SAINT = 2, POST = 3, NOTICE = 4;

    final int type;
    int textRes;          // HEADER / NOTICE
    int actionRes;        // NOTICE button label, 0 = no button
    Runnable action;      // NOTICE button click
    PrayerEntity prayer;
    SaintEntity saint;
    CommunityPost post;

    FeedItem(int type) { this.type = type; }

    static FeedItem header(int textRes) {
        FeedItem i = new FeedItem(HEADER); i.textRes = textRes; return i;
    }
    static FeedItem notice(int textRes, int actionRes, Runnable action) {
        FeedItem i = new FeedItem(NOTICE); i.textRes = textRes; i.actionRes = actionRes; i.action = action; return i;
    }
    static FeedItem prayer(PrayerEntity p) {
        FeedItem i = new FeedItem(DAILY_PRAYER); i.prayer = p; return i;
    }
    static FeedItem saint(SaintEntity s) {
        FeedItem i = new FeedItem(DAILY_SAINT); i.saint = s; return i;
    }
    static FeedItem post(CommunityPost p) {
        FeedItem i = new FeedItem(POST); i.post = p; return i;
    }
}
