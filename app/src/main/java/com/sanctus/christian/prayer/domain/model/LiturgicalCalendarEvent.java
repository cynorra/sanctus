package com.sanctus.christian.prayer.domain.model;

public class LiturgicalCalendarEvent {
    public enum Season { ADVENT, CHRISTMAS, ORDINARY, LENT, EASTER }

    private final String title;
    private final String date;
    private final Season season;
    private final String color;
    private final String description;
    private final boolean isFeastDay;

    public LiturgicalCalendarEvent(String title, String date, Season season, String color, String description, boolean isFeastDay) {
        this.title = title;
        this.date = date;
        this.season = season;
        this.color = color;
        this.description = description;
        this.isFeastDay = isFeastDay;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public Season getSeason() { return season; }
    public String getColor() { return color; }
    public String getDescription() { return description; }
    public boolean isFeastDay() { return isFeastDay; }
}
