package com.explore.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.Extra;
import com.explore.R;
import com.explore.adapters.ImageGalleryPagerAdapter;

import java.util.ArrayList;

/**
 * @author vgrec, created on 10/24/14.
 */
public class ImagesFragment extends Fragment {

    private ArrayList<String> urls = new ArrayList<String>();

    public static Fragment newInstance(Bundle extras) {
        ImagesFragment fragment = new ImagesFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urls.addAll(getArguments().getStringArrayList(Extra.PHOTOS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new ImageGalleryPagerAdapter(getActivity(), urls));
        return view;
    }
}
