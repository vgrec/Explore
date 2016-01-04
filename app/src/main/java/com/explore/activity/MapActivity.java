package com.explore.activity;

import android.os.Bundle;

import com.vgrec.explore.R;
import com.explore.fragment.ExploreMapFragment;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setActivityTitle();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, ExploreMapFragment.newInstance(getIntent().getExtras()))
                    .commit();
        }
    }

    private void setActivityTitle() {
        String address = getIntent().getExtras().getString(ExploreMapFragment.KEY_ADDRESS);
        int separatorIndex = address.indexOf(",");
        if (separatorIndex != -1) {
            setTitle(address.substring(0, separatorIndex));
        }
    }
}
