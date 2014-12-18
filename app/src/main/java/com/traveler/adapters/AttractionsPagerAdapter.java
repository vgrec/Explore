package com.traveler.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.traveler.models.google.PlaceType;
import com.traveler.fragment.AttractionsFragment;

/**
 * @author vgrec, created on 11/3/14.
 */
public class AttractionsPagerAdapter extends FragmentPagerAdapter {

    private final PlaceType[] placeTypes = PlaceType.values();

    public AttractionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return placeTypes[position].toString();
    }

    @Override
    public int getCount() {
        return placeTypes.length;
    }

    @Override
    public Fragment getItem(int position) {
        return AttractionsFragment.newInstance(placeTypes[position]);
    }

}
