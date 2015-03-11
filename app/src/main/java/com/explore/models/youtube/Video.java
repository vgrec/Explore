package com.explore.models.youtube;

/**
 * @author vgrec, created on 12/2/14.
 */
public class Video {
    private final String title;
    private final String thumbnail;
    private final String duration;
    private final String link;

    public Video(String title, String thumbnail, String duration, String link) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.link = link;
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

    public String getLink() {
        return link;
    }
}
