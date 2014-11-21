package com.traveler.models.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * @author vgrec, created on 9/5/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

    private Map<String, PageDescription> pages;

    public void setPages(Map<String, PageDescription> pages) {
        this.pages = pages;
    }

    public Map<String, PageDescription> getPages() {
        return pages;
    }

}
