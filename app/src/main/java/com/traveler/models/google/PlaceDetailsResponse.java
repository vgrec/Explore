package com.traveler.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author vgrec, created on 11/4/14.
 */
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
