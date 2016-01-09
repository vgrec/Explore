package com.explore.models.youtube;

import java.util.List;

public class VideosResponse {
    private List<Video> videos;

    public VideosResponse(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public int getSize() {
        return videos.size();
    }
}
