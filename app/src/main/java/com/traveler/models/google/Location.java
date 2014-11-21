package com.traveler.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author vgrec, created on 11/17/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
