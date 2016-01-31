package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThumbnailUrl {
    private String url;

    public String getUrl() {
        return url;
    }
}
