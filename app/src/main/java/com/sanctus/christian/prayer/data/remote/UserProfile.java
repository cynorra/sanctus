package com.sanctus.christian.prayer.data.remote;

import com.google.firebase.firestore.PropertyName;

/** Firestore document shape for users/{uid}. Needs a public no-arg constructor for deserialization. */
public class UserProfile {
    public String displayName;
    public String photoUrl;
    public long createdAt;
    public String referralCode;
    @PropertyName("invitedByUid")
    public String invitedByUid;
    public long inviteCount;

    public UserProfile() {}

    public UserProfile(String displayName, String photoUrl, long createdAt, String referralCode, String invitedByUid) {
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.referralCode = referralCode;
        this.invitedByUid = invitedByUid;
        this.inviteCount = 0;
    }
}
