package com.explore.models.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionResponse {

    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public PageDescription getPageDescription() {
        Map<String, PageDescription> val = getQuery().getPages();
        return new ArrayList<PageDescription>(val.values()).get(0);
    }
}
