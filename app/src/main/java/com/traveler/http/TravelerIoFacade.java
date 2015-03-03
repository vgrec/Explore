package com.traveler.http;

import com.traveler.models.google.PlaceType;

import java.util.ArrayList;

/**
 * @author vgrec, created on 8/22/14.
 */
public interface TravelerIoFacade {

    void getDescription();

    void getFlickrPhotos();

    void getVideos();

    void getPlaces(PlaceType placeType, String nextPageToken);

    void getPlaceDetails(String placeId);

    ArrayList<String> autocomplete(String input);
}
