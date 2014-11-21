package com.traveler.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.traveler.Constants;
import com.traveler.PlaceType;
import com.traveler.models.flickr.Photo;
import com.traveler.models.flickr.PhotosResponse;
import com.traveler.models.google.PlaceDetailsResponse;
import com.traveler.models.google.PlaceItemsResponse;
import com.traveler.models.wikipedia.DescriptionResponse;
import com.traveler.utils.Utils;

import java.util.List;

/**
 * @author vgrec, created on 8/22/14.
 */
public class TravelerIoFacadeImpl implements TravelerIoFacade {

    private Context context;
    private String location;
    private RequestQueue requestQueue;

    public TravelerIoFacadeImpl(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public TravelerIoFacade forLocation(String location) {
        this.location = location;
        return this;
    }

    // handle redirects, eg "slanic moldova"

    @Override
    public void getDescription(final TaskFinishedListener<DescriptionResponse> listener) {
        String url = String.format(Constants.Wikipedia.TEXT_SEARCH_URL, location);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DescriptionResponse descriptionResponse = Utils.fromJson(DescriptionResponse.class, response);
                listener.onSuccess(descriptionResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getPhotos(final TaskFinishedListener<List<Photo>> listener) {
        String url = String.format(Constants.Flickr.SEARCH_PHOTOS_URL, location);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PhotosResponse photosResponse = Utils.fromJson(PhotosResponse.class, response);
                listener.onSuccess(photosResponse.getPhotosResponse().getPhotos());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getPlaces(final TaskFinishedListener<PlaceItemsResponse> listener, PlaceType placeType) {
        String placeString = placeType.toString().toLowerCase();
        String url = String.format(Constants.Google.PLACES_URL, placeString, location, placeString);
        url = url.replaceAll(" ", "%20");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PlaceItemsResponse placeItemsResponse = Utils.fromJson(PlaceItemsResponse.class, response);
                listener.onSuccess(placeItemsResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getPlaceDetails(String placeId, final TaskFinishedListener<PlaceDetailsResponse> listener) {
        String url = String.format(Constants.Google.PLACE_DETAILS_URL, placeId);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PlaceDetailsResponse placeItemsResponse = Utils.fromJson(PlaceDetailsResponse.class, response);
                listener.onSuccess(placeItemsResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getVideos(TaskFinishedListener listener) {

    }
}
