package com.explore.models.youtube;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideosResponse {

    private Feed feed;

    public Feed getFeed() {
        return feed;
    }
}
