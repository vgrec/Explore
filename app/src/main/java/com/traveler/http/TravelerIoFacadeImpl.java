package com.traveler.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.traveler.Constants;
import com.traveler.models.events.AttractionsErrorEvent;
import com.traveler.models.events.FlickrPhotosErrorEvent;
import com.traveler.models.events.PlaceDetailsErrorEvent;
import com.traveler.models.events.VideosErrorEvent;
import com.traveler.models.flickr.PhotosResponse;
import com.traveler.models.google.PlaceDetailsResponse;
import com.traveler.models.google.PlaceItemsResponse;
import com.traveler.models.google.PlaceType;
import com.traveler.models.wikipedia.DescriptionResponse;
import com.traveler.models.youtube.VideosResponse;
import com.traveler.utils.Utils;

import de.greenrobot.event.EventBus;

/**
 * @author vgrec, created on 8/22/14.
 */
public class TravelerIoFacadeImpl implements TravelerIoFacade {

    private String location;
    private RequestQueue requestQueue;

    public TravelerIoFacadeImpl(Context context) {
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        location = TravelerSettings.getInstance(context).getLocation();
    }

    /**
     * Store specific settings related to TravelerIoFacade, eg, location, palette colors.
     */
    public static class TravelerSettings {

        public static final String LOCATION = "LOCATION";
        public static final String DARK_MUTED = "DARK_MUTED";
        public static final String PRIMARY_COLOR = "PRIMARY_COLOR";
        private final SharedPreferences settings;
        private static TravelerSettings instance;

        public TravelerSettings(Context context) {
            settings = context.getSharedPreferences("travl_settings", Context.MODE_PRIVATE);
        }

        public static TravelerSettings getInstance(Context context) {
            if (instance == null) {
                instance = new TravelerSettings(context);
            }
            return instance;
        }

        public void setLocation(String location) {
            settings.edit().putString(LOCATION, location).apply();
        }

        public String getLocation() {
            return settings.getString(LOCATION, "");
        }

        public void setDarkMutedColor(int color) {
            settings.edit().putInt(DARK_MUTED, color).apply();
            setPrimaryColor(color);
        }

        public int getDarkMutedColor() {
            return settings.getInt(DARK_MUTED, Color.DKGRAY);
        }

        public void setPrimaryColor(int color) {
            settings.edit().putInt(PRIMARY_COLOR, color).apply();
        }

        public int getPrimaryColor() {
            return getDarkMutedColor();
        }
    }

    // handle redirects, eg "slanic moldova"

    @Override
    public void getDescription() {
        String url = String.format(Constants.Wikipedia.TEXT_SEARCH_URL, location.replaceAll(" ", "%20"));
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DescriptionResponse descriptionResponse = Utils.fromJson(DescriptionResponse.class, response);
                EventBus.getDefault().post(descriptionResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                EventBus.getDefault().post(descriptionResponse);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getFlickrPhotos() {
        String url = String.format(Constants.Flickr.SEARCH_PHOTOS_URL, location + "%20city");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PhotosResponse photosResponse = Utils.fromJson(PhotosResponse.class, response);
                EventBus.getDefault().post(photosResponse.getPhotosResponse().getFlickrPhotos());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new FlickrPhotosErrorEvent(error));
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getPlaces(final PlaceType placeType, String nextPageToken) {
        String queryString = placeType.getQuery();
        String placeTypeString = placeType.getPlaceType();
        String url = String.format(Constants.Google.PLACES_URL, queryString, location, placeTypeString, nextPageToken);
        url = url.replaceAll(" ", "%20");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PlaceItemsResponse placeItemsResponse = Utils.fromJson(PlaceItemsResponse.class, response);
                if (placeItemsResponse != null && placeItemsResponse.getPlaces().size() > 0) {
                    placeItemsResponse.setPlaceType(placeType);
                    EventBus.getDefault().post(placeItemsResponse);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new AttractionsErrorEvent(error));
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getPlaceDetails(String placeId) {
        String url = String.format(Constants.Google.PLACE_DETAILS_URL, placeId);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PlaceDetailsResponse placeItemsResponse = Utils.fromJson(PlaceDetailsResponse.class, response);
                EventBus.getDefault().post(placeItemsResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new PlaceDetailsErrorEvent(error));
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void getVideos() {
        String location = (this.location) != null ? this.location : "Berlin";
        String url = String.format(Constants.Youtube.VIDEOS_URL, "travel in " + location);
        StringRequest request = new StringRequest(url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                VideosResponse videosResponse = Utils.fromJson(VideosResponse.class, response);
                EventBus.getDefault().post(videosResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new VideosErrorEvent(error));
            }
        });
        requestQueue.add(request);
    }
}
