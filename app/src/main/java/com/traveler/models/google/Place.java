package com.traveler.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vgrec, created on 11/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place implements Serializable {

    @JsonProperty("place_id")
    private String placeId;

    private String reference;
    private String name;
    private String rating;

    @JsonProperty("icon")
    private String iconUrl;

    @JsonProperty("formatted_address")
    private String address;

    @JsonProperty("international_phone_number")
    private String phoneNumber;

    @JsonProperty("website")
    private String webSite;

    private Geometry geometry;

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;

    private List<GooglePhoto> photos = new ArrayList<GooglePhoto>();
    private List<Review> reviews = new ArrayList<Review>();

    public String getPlaceId() {
        return placeId;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<GooglePhoto> getPhotos() {
        return photos;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebSite() {
        return webSite;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
