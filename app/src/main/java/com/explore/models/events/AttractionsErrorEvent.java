package com.explore.models.events;

import com.android.volley.VolleyError;

/**
 * author Unknown, created on 19.01.2015.
 */
public class AttractionsErrorEvent extends ErrorEvent {
    public AttractionsErrorEvent(VolleyError volleyError) {
        super(volleyError);
    }
}
