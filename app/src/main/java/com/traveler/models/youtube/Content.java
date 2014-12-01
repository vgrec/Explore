package com.traveler.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author Unknown, created on 30.11.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    @JsonProperty("$t")
    private String content;

    public String getText() {
        return content;
    }
}
