package com.traveler.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.traveler.R;
import com.traveler.adapters.AttractionsPagerAdapter;
import com.traveler.http.TravelerIoFacadeImpl;

public class AttractionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        int primaryColor = TravelerIoFacadeImpl.TravelerSettings.getInstance(this).getPrimaryColor();

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
        setTitle("Attractions in " + TravelerIoFacadeImpl.TravelerSettings.getInstance(this).getLocation());
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(primaryColor));
        }
    }
}
