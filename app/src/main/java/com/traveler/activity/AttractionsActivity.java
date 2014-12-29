package com.traveler.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.traveler.R;
import com.traveler.adapters.AttractionsPagerAdapter;
import com.traveler.http.TravelerIoFacadeImpl;

public class AttractionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        int primaryColor = TravelerIoFacadeImpl.TravelerSettings.getInstance(this).getPrimaryColor();

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new AttractionsPagerAdapter(getFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setBackgroundColor(primaryColor);
        tabs.setDividerColor(getResources().getColor(android.R.color.transparent));
        tabs.setTextSize(getResources().getDimensionPixelSize(R.dimen.tabs_text_size));
        tabs.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.tabs_indicator_height));
        tabs.setViewPager(pager);
        setTitle("Attractions in " + TravelerIoFacadeImpl.TravelerSettings.getInstance(this).getLocation());
        if (getActionBar() != null) {
            getActionBar().setBackgroundDrawable(new ColorDrawable(primaryColor));
        }
    }
}
