package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snippet {
    private String title;
    private Thumbnails thumbnails;

    public String getTitle() {
        return title;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }
}
