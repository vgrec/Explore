package com.traveler.models.flickr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotos {
    private String page;
    private int pages;

    @JsonProperty("perpage")
    private int perPage;

    @JsonProperty("photo")
    private List<FlickrPhoto> flickrPhotos;

    public String getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public List<FlickrPhoto> getFlickrPhotos() {
        return flickrPhotos;
    }
}
