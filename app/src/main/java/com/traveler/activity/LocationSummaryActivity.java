package com.traveler.activity;

import android.app.Activity;
import android.os.Bundle;

import com.traveler.Extra;
import com.traveler.R;
import com.traveler.fragment.LocationSummaryFragment;


public class LocationSummaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_summary);
        setTitle("");

        String location = getIntent().getStringExtra(Extra.LOCATION);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, LocationSummaryFragment.newInstance(location))
                    .commit();
        }
    }
}
