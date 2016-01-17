package com.explore.models.youtube;

public class Video {
    private final String title;
    private final String thumbnail;
    private final String duration;
    private final String videoId;

    public Video(String title, String thumbnail, String duration, String videoId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.videoId = videoId;
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

    public String getVideoId() {
        return videoId;
    }
}
