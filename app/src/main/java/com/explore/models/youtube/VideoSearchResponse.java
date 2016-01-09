package com.explore.models.youtube;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoSearchResponse {

    private List<SearchItem> items = new ArrayList<>();

    public List<SearchItem> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }
}
