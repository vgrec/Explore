package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDetails {
    private String duration;

    public String getDuration() {
        return duration;
    }
}
