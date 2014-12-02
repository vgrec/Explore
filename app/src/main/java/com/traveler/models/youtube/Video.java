package com.traveler.models.youtube;

/**
 * @author vgrec, created on 12/2/14.
 */
public class Video {
    private final String title;
    private final String thumbnail;
    private final String duration;

    public Video(String title, String thumbnail, String duration) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnail;
    }

    public String getDuration() {
        return duration;
    }
}
