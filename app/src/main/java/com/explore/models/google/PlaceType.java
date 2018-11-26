package com.explore.models.google;

public enum PlaceType {
    MUSEUM("museum", "museum"),
    RESTAURANT("restaurant", "restaurant"),
    SHOPPING("shops", "shopping_mall"),
    ZOO("zoo", "zoo"),
    CINEMA("cinema", "movie_theater");

    private String query;
    private String type;

    PlaceType(String query, String type) {
        this.query = query;
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public String getPlaceType() {
        return type;
    }
}
