package com.explore.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

    @JsonProperty("author_name")
    private String author;
    private String rating;
    private String text;
    private String time;

    public String getAuthor() {
        return author;
    }

    public String getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
