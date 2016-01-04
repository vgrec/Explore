package com.explore.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;
import com.vgrec.explore.R;

/**
 * Not used in V1.
 */
public class StreetViewActivity extends ActionBarActivity {

    private static final LatLng SYDNEY = new LatLng(44.4267674, 26.1025384);

    private StreetViewPanoramaView streetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        if (savedInstanceState == null) {
            options.position(SYDNEY);
        }

        streetView = new StreetViewPanoramaView(this, options);
        addContentView(streetView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        streetView.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        streetView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        streetView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        streetView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        streetView.onSaveInstanceState(outState);
    }
}
