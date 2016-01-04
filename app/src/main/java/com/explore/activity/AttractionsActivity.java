package com.explore.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.vgrec.explore.R;
import com.explore.adapters.AttractionsPagerAdapter;
import com.explore.http.TravelerIoFacadeImpl;

public class AttractionsActivity extends BaseActivity {

    public static final String TITLE = "TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new AttractionsPagerAdapter(getFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabs.setDividerColor(getResources().getColor(android.R.color.transparent));
        tabs.setTextSize(getResources().getDimensionPixelSize(R.dimen.tabs_text_size));
        tabs.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.tabs_indicator_height));
        tabs.setViewPager(pager);

        String title = null;
        if (getIntent() != null) {
            title = getIntent().getStringExtra(TITLE);
        }

        setTitle("Attractions in " + (title != null ? title : TravelerIoFacadeImpl.TravelerSettings.getInstance(this).getLocation()));
    }
}
