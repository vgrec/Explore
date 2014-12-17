package com.traveler.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.Extra;
import com.traveler.R;
import com.traveler.adapters.ImageGalleryPagerAdapter;
import com.traveler.models.flickr.Photo;

import java.util.ArrayList;

/**
 * @author vgrec, created on 10/24/14.
 */
public class ImagesFragment extends Fragment {

    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ViewPager viewPager;

    public static Fragment newInstance(Bundle extras) {
        ImagesFragment fragment = new ImagesFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photos.addAll((ArrayList<Photo>) getArguments().getSerializable(Extra.PHOTOS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new ImageGalleryPagerAdapter(getActivity(), photos));
        return view;
    }
}
