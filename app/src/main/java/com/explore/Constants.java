package com.explore;

/**
 * @author vgrec, created on 9/4/14.
 */
public class Constants {

    public class Wikipedia {
        public static final String TEXT_SEARCH_URL = "http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles=%s&redirects=";
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


        // Search example:
        // https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=97e9fc9835ab601cee0fe21fce7b11aa&text=Saharna&license=4%2C5&sort=relevance&format=json&nojsoncallback=1&api_sig=f7a64fb60fdff1b16ce5d5cb5aac3909

        // Get Photo By Id:
        // https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=97e9fc9835ab601cee0fe21fce7b11aa&photo_id=190157681&format=json&nojsoncallback=1&api_sig=cb40aac2fd7b3fe82800e2e05acabe42
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

        // Examples

        // Museum:
        // https://maps.googleapis.com/maps/api/place/textsearch/json?query=museum in Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24&types=museum

        // Restaurant
        // https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurant%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24&types=restaurant

        // Shopping
        // https://maps.googleapis.com/maps/api/place/textsearch/json?query=shopping%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

        // Details google
        // https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJj57Sam5aqEcRTHQWXLfcDjk&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

        // Photo google
        // https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRsAAAAivj_hV3wnoOQeOAIziW8dzA1dbMW49embKN0F49aJCzmoN8uOCFEFNzoN_hnj6fMmLdcntBU061SFO_0IySeR8eYl_ziMe0xW2VVQMYWbHVXsP1bANuh74YDvyPgZnBoAhymKQkM1tDxmB2HYfyV4hIQqnHDwjU_ZxW6EtFTDoTn6RoUSQcZORA8IgZM3ql1yZzZ3iZIAhk&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24
    }

    public class Youtube {
        private static final int max = 40;

        // http://gdata.youtube.com/feeds/base/videos?q=travel%20in%20chisinau&max-results=40
        public static final String VIDEOS_URL = "http://gdata.youtube.com/feeds/base/videos?q=%s&alt=json&max-results=" + max;
    }

    // =============================================================================================
    // We collect information from various sources like
    // Google Places, Google Translate, Youtube, Google Books, Flickr, Eventbrite, Expedia, Forecast.io, Wikipedia, Google Maps and Google Street View.

    // Autocomplete
    // https://developers.google.com/places/training/autocomplete-android
}
