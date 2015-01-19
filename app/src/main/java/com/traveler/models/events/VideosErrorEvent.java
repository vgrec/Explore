package com.traveler.models.events;

import com.android.volley.VolleyError;

/**
 * author Unknown, created on 19.01.2015.
 */
public class VideosErrorEvent extends ErrorEvent {
    public VideosErrorEvent(VolleyError volleyError) {
        super(volleyError);
    }
}
