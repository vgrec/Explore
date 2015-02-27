package com.traveler.models.flickr;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosResponse {
    @JsonProperty("photos")
    private FlickrPhotos flickrPhotos;

    public FlickrPhotos getPhotosResponse() {
        return flickrPhotos;
    }
}
