package com.explore.models.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDetailsResponse {
    private List<DetailItem> items = new ArrayList<>();

    public List<DetailItem> getItems() {
        return items;
    }
}
