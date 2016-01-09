package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchItem {
    @JsonProperty("id")
    private ResourceId resourceId;
    private Snippet snippet;

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Snippet getSnippet() {
        return snippet;
    }

}
