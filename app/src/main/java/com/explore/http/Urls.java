package com.explore.http;

import android.net.Uri;

import com.explore.models.flickr.FlickrPhoto;
import com.explore.models.flickr.Size;
import com.explore.models.google.PlaceType;
import com.explore.models.youtube.VideoSearchResponse;
import com.explore.utils.Utils;
import com.explore.utils.VideoUtils;
import com.vgrec.explore.BuildConfig;

/**
 * Helper class that provides all the URLs the application uses.
 * <p/>
 * Author vgrec, on 07.02.16.
 */
public class Urls {

    public static final String GOOGLE_PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place";
    public static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3";
    public static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest";

    // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
    public static final String FLICKR_PHOTO_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    public static final String WIKIPEDIA_BASE_URL = "https://en.wikipedia.org/w/api.php";

    public static String getSearchPlacesUrl(PlaceType placeType, String nextPageToken, String location) {
        Uri uri = Uri.parse(GOOGLE_PLACES_BASE_URL + "/textsearch/json")
                .buildUpon()
                .appendQueryParameter("query", placeType.getPlaceType() + " in " + location)
                .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
                .appendQueryParameter("types", placeType.getPlaceType())
                .appendQueryParameter("pagetoken", nextPageToken)
                .build();
        return uri.toString();
    }

    public static String getPlaceDetailsUrl(String placeId) {
        Uri uri = Uri.parse(GOOGLE_PLACES_BASE_URL + "/details/json")
                .buildUpon()
                .appendQueryParameter("placeid", placeId)
                .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
                .build();
        return uri.toString();
    }

    public static String getVideoDetailsUrl(VideoSearchResponse videosResponse) {
        Uri uri = Uri.parse(YOUTUBE_BASE_URL + "/videos")
                .buildUpon()
                .appendQueryParameter("key", BuildConfig.YOUTUBE_API_KEY)
                .appendQueryParameter("id", VideoUtils.buildIdsString(videosResponse.getItems()))
                .appendQueryParameter("part", "contentDetails")
                .build();
        return uri.toString();
    }

    public static String getSearchVideosUrl(String location) {
        Uri uri = Uri.parse(YOUTUBE_BASE_URL + "/search")
                .buildUpon()
                .appendQueryParameter("key", BuildConfig.YOUTUBE_API_KEY)
                .appendQueryParameter("q", location)
                .appendQueryParameter("maxResults", "45")
                .appendQueryParameter("type", "video")
                .appendQueryParameter("part", "id,snippet").build();

        return uri.toString();
    }

    public static String getSearchFlickrPhotosUrl(String location) {
        Uri uri = Uri.parse(FLICKR_BASE_URL)
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("api_key", BuildConfig.FLICKR_API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("per_page", "15")
                .appendQueryParameter("license", "4,5,6") // non commercial
                .appendQueryParameter("sort", "relevance")
                .appendQueryParameter("text", location + " city") // narrow down the search term
                .build();
        return uri.toString();
    }

    public static String getFlickrImageUrl(FlickrPhoto flickrPhoto, Size size) {
        return String.format(FLICKR_PHOTO_URL, flickrPhoto.getFarm(), flickrPhoto.getServer(), flickrPhoto.getId(), flickrPhoto.getSecret(), size);
    }

    public static String getWikipediaDescriptionUrl(String location) {
        Uri uri = Uri.parse(WIKIPEDIA_BASE_URL)
                .buildUpon()
                .appendQueryParameter("action", "query")
                .appendQueryParameter("prop", "extracts")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("exlimit", "10")
                .appendQueryParameter("explaintext", "true")
                .appendQueryParameter("redirects", "false")
                .appendQueryParameter("titles", location)
                .build();
        return uri.toString();
    }

    public static String getAutocompleteUrl(String input) {
        Uri uri = Uri.parse(GOOGLE_PLACES_BASE_URL + "/autocomplete/json")
                .buildUpon()
                .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
                .appendQueryParameter("input", Utils.encodeAsUrl(input))
                .build();
        return uri.toString();
    }

    public static String getPlaceThumbnailUrl(String photoReference) {
        Uri uri = Uri.parse(GOOGLE_PLACES_BASE_URL + "/photo")
                .buildUpon()
                .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
                .appendQueryParameter("maxwidth", "250")
                .appendQueryParameter("photoreference", photoReference)
                .build();
        return uri.toString();
    }

    public static String getPlaceImageUrl(String photoReference) {
        Uri uri = Uri.parse(GOOGLE_PLACES_BASE_URL + "/photo")
                .buildUpon()
                .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
                .appendQueryParameter("maxwidth", "400")
                .appendQueryParameter("photoreference", photoReference)
                .build();
        return uri.toString();
    }
}
