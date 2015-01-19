package com.traveler.models.events;

import com.android.volley.VolleyError;

/**
 * author Unknown, created on 19.01.2015.
 */
public class ErrorEvent {
    private final VolleyError volleyError;

    public ErrorEvent(VolleyError volleyError) {
        this.volleyError = volleyError;

    }

    public VolleyError getVolleyError() {
        return volleyError;
    }
}
