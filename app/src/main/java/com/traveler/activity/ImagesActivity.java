package com.traveler.activity;

import android.app.Activity;
import android.os.Bundle;

import com.traveler.R;
import com.traveler.fragment.ImagesFragment;

public class ImagesActivity extends Activity {

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
