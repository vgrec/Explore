package com.explore.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.explore.Constants;
import com.explore.models.events.AttractionsErrorEvent;
import com.explore.models.events.FlickrPhotosErrorEvent;
import com.explore.models.events.PlaceDetailsErrorEvent;
import com.explore.models.events.VideosErrorEvent;
import com.explore.models.flickr.PhotosResponse;
import com.explore.models.google.PlaceDetailsResponse;
import com.explore.models.google.PlaceItemsResponse;
import com.explore.models.google.PlaceType;
import com.explore.models.wikipedia.DescriptionResponse;
import com.explore.models.youtube.VideoDetailsResponse;
import com.explore.models.youtube.VideoSearchResponse;
import com.explore.models.youtube.VideosResponse;
import com.explore.utils.Utils;
import com.explore.utils.VideoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
                EventBus.getDefault().post(error);
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
        Uri uri = Uri.parse("https://www.googleapis.com/youtube/v3/search")
                .buildUpon()
                .appendQueryParameter("key", "AIzaSyACYHmq3nim1ozMWgS63K2XBlP7N7jQLQA")
                .appendQueryParameter("q", location.replaceAll(" ", "%20"))
                .appendQueryParameter("maxResults", "45")
                .appendQueryParameter("type", "video")
                .appendQueryParameter("part", "id,snippet").build();

        StringRequest request = new StringRequest(uri.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                VideoSearchResponse videoSearchResponse = Utils.fromJson(VideoSearchResponse.class, response);
                if (videoSearchResponse != null && videoSearchResponse.getSize() > 0) {
                    getVideoDetails(videoSearchResponse);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new VideosErrorEvent(error));
            }
        });

        requestQueue.add(request);
    }

    private void getVideoDetails(final VideoSearchResponse videosResponse) {
        Uri uri = Uri.parse("https://www.googleapis.com/youtube/v3/videos")
                .buildUpon()
                .appendQueryParameter("key", "AIzaSyACYHmq3nim1ozMWgS63K2XBlP7N7jQLQA")
                .appendQueryParameter("id", VideoUtils.buildIdsString(videosResponse.getItems()))
                .appendQueryParameter("part", "contentDetails").build();

        StringRequest request = new StringRequest(uri.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VideoDetailsResponse detailsResponse = Utils.fromJson(VideoDetailsResponse.class, response);
                VideosResponse videos = VideoUtils.join(videosResponse, detailsResponse);
                EventBus.getDefault().post(videos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new VideosErrorEvent(error));
            }
        });

        requestQueue.add(request);
    }

    @Override
    public ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        String url = String.format(Constants.Google.AUTOCOMPLETE_URL, Utils.encodeAsUrl(input));
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
        requestQueue.add(request);

        try {
            JSONObject response = future.get(10, TimeUnit.SECONDS);
            JSONArray predictions = response.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predictions.length());
            for (int i = 0; i < predictions.length(); i++) {
                JSONArray terms = predictions.getJSONObject(i).getJSONArray("terms");
                resultList.add(terms.getJSONObject(0).getString("value"));
            }

        } catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
