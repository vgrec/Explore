package com.traveler.http;

import com.android.volley.VolleyError;

/**
 * @author vgrec, created on 8/22/14.
 */
public abstract class TaskFinishedListener<T> {

    protected abstract void onSuccess(T result);

    protected void onFailure(VolleyError error) {
    }

}
