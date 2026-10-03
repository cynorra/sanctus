package com.sanctus.christian.prayer.data.remote;

import com.google.firebase.firestore.Exclude;

/** Firestore document shape for posts/{postId}. Needs a public no-arg constructor for deserialization. */
public class CommunityPost {
    public String authorUid;
    public String authorName;
    public String text;
    public long createdAt;
    public long amenCount;
    public long reportCount;

    /** Filled from the document id / amens subcollection after load; never written to Firestore. */
    @Exclude public String id;
    @Exclude public boolean amenedByMe;

    public CommunityPost() {}

    public CommunityPost(String authorUid, String authorName, String text, long createdAt) {
        this.authorUid = authorUid;
        this.authorName = authorName;
        this.text = text;
        this.createdAt = createdAt;
        this.amenCount = 0;
        this.reportCount = 0;
    }
}
