package com.traveler.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.traveler.Extra;
import com.traveler.fragment.PlaceDetailFragment;

public class PlaceDetailActivity extends ActionBarActivity {

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
