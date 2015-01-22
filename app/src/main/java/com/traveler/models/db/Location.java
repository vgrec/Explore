package com.traveler.models.db;

/**
 * @author vgrec, created on 1/22/15.
 */
public class Location {

    /**
     * LOCALITY = Country or City
     * PLACE = concrete place, eg. "Restaurant Bonviery"
     */
    public enum Type {
        LOCALITY, PLACE
    }

    private long id;
    private String imageUrl;
    private String title;
    private Type type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
