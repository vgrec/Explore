package com.traveler.activity;

import android.app.Activity;
import android.os.Bundle;

import com.traveler.R;
import com.traveler.fragment.VideosFragment;

public class VideosActivity extends Activity {

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
