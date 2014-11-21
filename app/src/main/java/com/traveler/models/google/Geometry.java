package com.traveler.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author vgrec, created on 11/17/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
    private Location location;

    public Location getLocation() {
        return location;
    }
}
