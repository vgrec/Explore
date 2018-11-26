package com.explore.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.explore.adapters.AttractionsPagerAdapter;
import com.explore.http.ExploreHttpFacadeImpl;
import com.vgrec.explore.R;

public class AttractionsActivity extends BaseActivity {

    public static final String TITLE = "TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new AttractionsPagerAdapter(getFragmentManager()));
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabs.setupWithViewPager(pager);

        String title = null;
        if (getIntent() != null) {
            title = getIntent().getStringExtra(TITLE);
        }

        title = (title != null) ? title : ExploreHttpFacadeImpl.InternalExploreSettings.getInstance(this).getLocation();
        setTitle(getString(R.string.attractions_in_title_prefix) + " " + title);
    }
}
