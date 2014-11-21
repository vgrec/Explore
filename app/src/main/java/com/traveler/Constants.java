package com.traveler;

/**
 * @author vgrec, created on 9/4/14.
 */
public class Constants {
    private static final int IMAGES_PER_PAGE = 15;

    public class Wikipedia {
        public static final String TEXT_SEARCH_URL = "http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles=%s&redirects=";
    }

    public class Flickr {
        private static final String API_KEY = "97e9fc9835ab601cee0fe21fce7b11aa";
        private static final String BASE_URL = "https://api.flickr.com/services/rest/";
        public static final String SEARCH_PHOTOS_URL = BASE_URL + "?method=flickr.photos.search&api_key=" + API_KEY + "&format=json&nojsoncallback=1&per_page=" + IMAGES_PER_PAGE + "&license=4,5,6&sort=relevance&text=%s";

        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
        public static final String PHOTO_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
    }

    public class Google {
        private static final int thumbnailWidth = 250;
        private static final int imageWidth = 400;
        private static final String PLACES_API_KEY = "AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24";
        private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";

        // query={place, eg "restaurant"} in {city}&key=" + PLACES_API_KEY + "&types={place}
        public static final String PLACES_URL = BASE_URL + "/textsearch/json?query=%s in %s&key=" + PLACES_API_KEY + "&types=%s";
        public static final String THUMBNAIL_URL = BASE_URL + "/photo?maxwidth=" + thumbnailWidth + "&photoreference=%s&key=" + PLACES_API_KEY;
        public static final String IMAGE_URL = BASE_URL + "/photo?maxwidth=" + imageWidth + "&photoreference=%s&key=" + PLACES_API_KEY;
        public static final String PLACE_DETAILS_URL = BASE_URL + "/details/json?placeid=%s&key=" + PLACES_API_KEY;
    }

    // ==================
    //

    // formatted_address
    // international_phone_number
    // formatted_phone_number
    // name
    // opening hours {open_now, weekday_text}
    // photos
    // rating
    // reviews
    // website


    // =============================================================================================
    // We collect information from various sources like
    // Google Places, Google Translate, Youtube, Google Books, Flickr, Eventbrite, Expedia, Forecast.io, Wikipedia, Google Maps and Google Street View.

    // api key:
    // AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24


    // https://developers.google.com/places/training/autocomplete-android


    // Details google
    // https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJj57Sam5aqEcRTHQWXLfcDjk&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

    // foto google
    // https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRsAAAAivj_hV3wnoOQeOAIziW8dzA1dbMW49embKN0F49aJCzmoN8uOCFEFNzoN_hnj6fMmLdcntBU061SFO_0IySeR8eYl_ziMe0xW2VVQMYWbHVXsP1bANuh74YDvyPgZnBoAhymKQkM1tDxmB2HYfyV4hIQqnHDwjU_ZxW6EtFTDoTn6RoUSQcZORA8IgZM3ql1yZzZ3iZIAhk&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

    // ============= Flickr
    // https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=76e55981bd5910ca10a65dfead917d7a&text=Saharna&license=4%2C5&sort=relevance&format=json&nojsoncallback=1&api_sig=f7a64fb60fdff1b16ce5d5cb5aac3909

    // Get Photo By Id:
    // https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=76e55981bd5910ca10a65dfead917d7a&photo_id=190157681&format=json&nojsoncallback=1&api_sig=cb40aac2fd7b3fe82800e2e05acabe42


    // Museum:
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=museum in Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24&types=museum

    // Restaurant
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurant%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24&types=restaurant

    // Shopping
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=shopping%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

    // Zoo
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=zoo%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24&types=zoo

    // Theaters
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=theater%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

    // Cinema
    // https://maps.googleapis.com/maps/api/place/textsearch/json?query=cinema%20in%20Chisinau&key=AIzaSyBVcUHbF6cYBhgUBWUvLGLuwx6B8-tSj24

    // =======================
    // Images,
    // Description,
    // Attractions,
    // StreetView,
    // Videos


}
