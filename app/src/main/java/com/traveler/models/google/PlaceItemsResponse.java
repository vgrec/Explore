package com.traveler.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author vgrec, created on 11/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceItemsResponse {
    @JsonProperty("next_page_token")
    private String nextPageToken;

    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;

    @JsonProperty("results")
    private List<Place> getPlaces;

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public List<Place> getPlaces() {
        return getPlaces;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
