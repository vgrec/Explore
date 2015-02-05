package com.traveler.activity;

import android.os.Bundle;

import com.traveler.R;
import com.traveler.fragment.LocationSummaryFragment;


public class LocationSummaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_summary);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LocationSummaryFragment())
                    .commit();
        }

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.transparent));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
