package com.traveler.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.traveler.R;
import com.traveler.fragment.ImagesFragment;

public class ImagesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ImagesFragment.newInstance(getIntent().getExtras()))
                    .commit();
        }
    }
}
