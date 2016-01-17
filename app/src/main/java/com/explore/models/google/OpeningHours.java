package com.explore.models.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningHours {
    @JsonProperty("open_now")
    private boolean isOpened;

    @JsonProperty("weekday_text")
    private List<String> weekdayText = new ArrayList<String>();

    public boolean isOpened() {
        return isOpened;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }
}
