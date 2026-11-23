package com.example.practicalmidexam;

public class Artwork {
    private final String title;
    private final String artist;
    private final String year;
    private final int imageResId;
    private final String websiteUrl;
    private final String description;

    public Artwork(String title, String artist, String year, int imageResId, String websiteUrl, String description) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.imageResId = imageResId;
        this.websiteUrl = websiteUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getYear() {
        return year;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getDescription() {
        return description;
    }
}
