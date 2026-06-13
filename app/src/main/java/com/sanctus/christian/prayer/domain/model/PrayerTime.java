package com.sanctus.christian.prayer.domain.model;

public class PrayerTime {
    private final String name;
    private final String time;
    private final String nameLocalized;
    private boolean notificationEnabled;

    public PrayerTime(String name, String nameLocalized, String time, boolean notificationEnabled) {
        this.name = name;
        this.nameLocalized = nameLocalized;
        this.time = time;
        this.notificationEnabled = notificationEnabled;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getNameLocalized() { return nameLocalized; }
    public boolean isNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(boolean enabled) { this.notificationEnabled = enabled; }
}
