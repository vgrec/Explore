package com.explore.models.events;

import com.android.volley.VolleyError;

/**
 * author Unknown, created on 19.01.2015.
 */
public class PlaceDetailsErrorEvent extends ErrorEvent{
    public PlaceDetailsErrorEvent(VolleyError volleyError) {
        super(volleyError);
    }
}
