package com.traveler.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author vgrec, created on 12/3/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    private String type;
    private String href;

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }
}
