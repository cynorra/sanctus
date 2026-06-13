package com.sanctus.christian.prayer.domain.model;

import java.util.List;

public class RosaryMystery {
    public enum Type { JOYFUL, SORROWFUL, GLORIOUS, LUMINOUS }

    private final String title;
    private final Type type;
    private final List<String> mysteries;
    private final String dayOfWeek;

    public RosaryMystery(String title, Type type, List<String> mysteries, String dayOfWeek) {
        this.title = title;
        this.type = type;
        this.mysteries = mysteries;
        this.dayOfWeek = dayOfWeek;
    }

    public String getTitle() { return title; }
    public Type getType() { return type; }
    public List<String> getMysteries() { return mysteries; }
    public String getDayOfWeek() { return dayOfWeek; }
}
