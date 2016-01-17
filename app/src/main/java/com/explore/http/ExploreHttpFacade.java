package com.explore.http;

import com.explore.models.google.PlaceType;

import java.util.ArrayList;

/**
 * Interface defining all the methods the application uses that interacts with the HTTP.
 *
 * @author vgrec, created on 8/22/14.
 */
public interface ExploreHttpFacade {

    void getDescription();

    void getFlickrPhotos();

    void getVideos();

    void getPlaces(PlaceType placeType, String nextPageToken);

    void getPlaceDetails(String placeId);

    ArrayList<String> autocomplete(String input);
}
