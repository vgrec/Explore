package com.traveler.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.traveler.R;
import com.traveler.fragment.VideosFragment;

public class VideosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new VideosFragment())
                    .commit();
        }
    }
}
