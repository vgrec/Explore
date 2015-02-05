package com.traveler.activity;

import android.os.Bundle;

import com.traveler.Extra;
import com.traveler.fragment.PlaceDetailFragment;

public class PlaceDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        String placeId = getIntent().getStringExtra(Extra.PLACE_ID);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, PlaceDetailFragment.newInstance(placeId))
                    .commit();
        }
    }
}
