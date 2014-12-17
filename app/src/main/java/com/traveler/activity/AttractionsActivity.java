package com.traveler.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.traveler.Extra;
import com.traveler.R;
import com.traveler.adapters.AttractionsPagerAdapter;

public class AttractionsActivity extends Activity {

    private String city;
    private int vibrantColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        city = getIntent().getStringExtra(Extra.LOCATION);

        vibrantColor = getIntent().getIntExtra(Extra.VIBRANT_COLOR, Color.DKGRAY);
        if (getActionBar() != null) {
            configureActionBar(vibrantColor);
        }

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new AttractionsPagerAdapter(getFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setBackgroundColor(vibrantColor);
        tabs.setDividerColor(getResources().getColor(android.R.color.transparent));
        tabs.setTextSize(getResources().getDimensionPixelSize(R.dimen.tabs_text_size));
        tabs.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.tabs_indicator_height));
        tabs.setViewPager(pager);
    }

    private void configureActionBar(int vibrantColor) {
        if (getActionBar() != null) {
            getActionBar().setBackgroundDrawable(new ColorDrawable(vibrantColor));
        }
        TextView textView = new TextView(this);
        textView.setText("Attractions in " + city);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(getResources().getDimension(R.dimen.actionbar_text_size));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        getActionBar().setCustomView(textView);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
    }

    public String getCity() {
        return city;
    }

    public int getVibrantColor() {
        return vibrantColor;
    }
}
