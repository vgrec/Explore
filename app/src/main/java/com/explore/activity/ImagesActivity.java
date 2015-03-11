package com.explore.activity;

import android.os.Bundle;

import com.explore.R;
import com.explore.fragment.ImagesFragment;

public class ImagesActivity extends BaseActivity {

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
