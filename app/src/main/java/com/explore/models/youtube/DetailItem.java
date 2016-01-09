package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailItem {
    private String id;
    private ContentDetails contentDetails;

    public String getId() {
        return id;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }
}
