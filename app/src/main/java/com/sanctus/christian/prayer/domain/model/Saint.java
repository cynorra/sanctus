package com.sanctus.christian.prayer.domain.model;

public class Saint {
    private final int id;
    private final String name;
    private final String description;
    private final String feastDate;
    private final String patronOf;
    private final String imageUrl;

    public Saint(int id, String name, String description, String feastDate, String patronOf, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feastDate = feastDate;
        this.patronOf = patronOf;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFeastDate() { return feastDate; }
    public String getPatronOf() { return patronOf; }
    public String getImageUrl() { return imageUrl; }
}
