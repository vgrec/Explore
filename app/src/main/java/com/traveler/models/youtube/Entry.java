package com.traveler.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {

    private Title title;
    private Content content;
    @JsonProperty("link")
    private List<Link> links = new ArrayList<Link>();

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public List<Link> getLinks() {
        return links;
    }
}
