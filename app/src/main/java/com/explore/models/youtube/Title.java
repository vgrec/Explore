package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author Unknown, created on 30.11.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Title {

    @JsonProperty("$t")
    private String title;

    public String getText() {
        return title;
    }
}
