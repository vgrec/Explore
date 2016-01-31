package com.explore;

/**
 * @author vgrec, created on 9/4/14.
 */
public class Constants {

    public static final String TAG = "Explore";

    public class Wikipedia {
        public static final String TEXT_SEARCH_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles=%s&redirects=false";
    }

    public class Flickr {
        // Flickr API keys and their terms of use:
        // http://stackoverflow.com/a/10362918/1271435

        private static final int flickrPhotosPerPage = 15;
        private static final String API_KEY = "97e9fc9835ab601cee0fe21fce7b11aa";
        private static final String BASE_URL = "https://api.flickr.com/services/rest/";
        public static final String SEARCH_PHOTOS_URL = BASE_URL + "?method=flickr.photos.search&api_key=" + API_KEY + "&format=json&nojsoncallback=1&per_page=" + flickrPhotosPerPage + "&license=4,5,6&sort=relevance&text=%s";

        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
        public static final String PHOTO_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
    }

    public class Google {
        private static final int thumbnailWidth = 250;
        private static final int imageWidth = 400;
        private static final String PLACES_API_KEY = "AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24";
        private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";

        // query={place, eg "restaurant"} in {city}&key=" + PLACES_API_KEY + "&types={place}
        public static final String PLACES_URL = BASE_URL + "/textsearch/json?query=%s in %s&key=" + PLACES_API_KEY + "&types=%s&pagetoken=%s";
        public static final String THUMBNAIL_URL = BASE_URL + "/photo?maxwidth=" + thumbnailWidth + "&photoreference=%s&key=" + PLACES_API_KEY;
        public static final String IMAGE_URL = BASE_URL + "/photo?maxwidth=" + imageWidth + "&photoreference=%s&key=" + PLACES_API_KEY;
        public static final String PLACE_DETAILS_URL = BASE_URL + "/details/json?placeid=%s&key=" + PLACES_API_KEY;
        public static final String AUTOCOMPLETE_URL = BASE_URL + "/autocomplete/json?key=" + PLACES_API_KEY + "&input=%s";
    }

    public class Youtube {
        private static final int max = 40;

        // http://gdata.youtube.com/feeds/base/videos?q=travel%20in%20chisinau&max-results=40
        public static final String VIDEOS_URL = "http://gdata.youtube.com/feeds/base/videos?q=%s&alt=json&max-results=" + max;
    }

}
