package com.explore.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse {
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;

    @JsonProperty("result")
    private Place place;

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public Place getPlace() {
        return place;
    }
}
