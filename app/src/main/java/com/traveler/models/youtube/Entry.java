package com.traveler.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {

    private Title title;
    private Content content;

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }
}
