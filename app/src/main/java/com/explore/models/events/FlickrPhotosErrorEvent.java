package com.explore.models.events;

import com.android.volley.VolleyError;

/**
 * @author vgrec, created on 2/20/15.
 */
public class FlickrPhotosErrorEvent extends ErrorEvent {
    public FlickrPhotosErrorEvent(VolleyError volleyError) {
        super(volleyError);
    }
}
