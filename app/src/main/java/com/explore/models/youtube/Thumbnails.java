package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Thumbnails {

    @JsonProperty("default")
    private ThumbnailUrl defaultUrl;

    @JsonProperty("medium")
    private ThumbnailUrl mediumUrl;

    @JsonProperty("high")
    private ThumbnailUrl highUrl;

    public String getDefaultUrl() {
        return defaultUrl != null ? defaultUrl.getUrl() : "";
    }

    public String getMediumUrl() {
        return mediumUrl != null ? mediumUrl.getUrl() : "";
    }

    public String getHighUrl() {
        return highUrl != null ? highUrl.getUrl() : "";
    }
}
