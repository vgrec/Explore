package com.traveler.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.traveler.Extra;
import com.traveler.fragment.PlaceDetailFragment;

public class PlaceDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        String placeId = getIntent().getStringExtra(Extra.PLACE_ID);
        int vibrantColor = getIntent().getIntExtra(Extra.VIBRANT_COLOR, Color.BLUE);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, PlaceDetailFragment.newInstance(placeId, vibrantColor))
                    .commit();
        }
    }
}
