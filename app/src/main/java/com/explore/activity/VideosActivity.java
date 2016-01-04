package com.explore.activity;

import android.os.Bundle;

import com.vgrec.explore.R;
import com.explore.fragment.VideosFragment;

public class VideosActivity extends BaseActivity {

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
