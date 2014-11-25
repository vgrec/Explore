package com.traveler.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.traveler.Constants;
import com.traveler.Extra;
import com.traveler.ImageHelper;
import com.traveler.R;
import com.traveler.http.TaskFinishedListener;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.models.google.Place;
import com.traveler.models.google.PlaceDetailsResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author vgrec, created on 11/17/14.
 */

// http://blog.syedgakbar.com/2012/07/changing-color-of-the-drawable-or-imageview-at-runtime-in-android/
public class PlaceDetailFragment extends Fragment {

    private String placeId;
    private int vibrantColor = Color.BLUE; // default;
    private PlaceDetailsResponse placeResponse;

    @InjectView(R.id.place_detail_picture)
    NetworkImageView placeDetailImageView;

    @InjectView(R.id.place_name)
    TextView nameTextView;

    @InjectView(R.id.place_address)
    TextView addressTextView;

    @InjectView(R.id.phone_number)
    TextView phoneNumberTextView;

    @InjectView(R.id.rating)
    TextView ratingTextView;

    @InjectView(R.id.web_site)
    TextView webSiteTextView;

    @InjectView(R.id.details_header_container)
    ViewGroup detailsHeaderContainer;


    public static Fragment newInstance(String placeId, int vibrantColor) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putString(Extra.PLACE_ID, placeId);
        args.putInt(Extra.VIBRANT_COLOR, vibrantColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            placeId = getArguments().getString(Extra.PLACE_ID);
            vibrantColor = getArguments().getInt(Extra.VIBRANT_COLOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (placeResponse == null) {
            downloadPlaceDetailsAndUpdateUi();
        } else {
            updateUi();
        }
    }

    private void downloadPlaceDetailsAndUpdateUi() {
        TravelerIoFacadeImpl ioFacade = new TravelerIoFacadeImpl(getActivity());
        ioFacade.getPlaceDetails(placeId, new TaskFinishedListener<PlaceDetailsResponse>() {
            @Override
            protected void onSuccess(PlaceDetailsResponse result) {
                placeResponse = result;
                updateUi();
            }
        });
    }

    private void updateUi() {
        Place place = placeResponse.getPlace();
        if (place == null) {
            return;
        }

        nameTextView.setText(place.getName());
        detailsHeaderContainer.setBackgroundColor(vibrantColor);
        addressTextView.setText(place.getAddress());
        phoneNumberTextView.setText(place.getPhoneNumber());
        ratingTextView.setText(place.getRating());
        webSiteTextView.setText(place.getWebSite());

        setColorFor(addressTextView, phoneNumberTextView, webSiteTextView);

        if (place.getPhotos().size() > 0) {
            String url = String.format(Constants.Google.IMAGE_URL, place.getPhotos().get(0).getPhotoReference());
            ImageHelper.loadImage(getActivity(), url, placeDetailImageView);
        }

    }

    private void setColorFor(TextView... textViews) {
        for (TextView textView : textViews) {
            Drawable[] drawables = textView.getCompoundDrawables();
            Drawable leftDrawable = drawables[0];
            leftDrawable.setColorFilter(vibrantColor, PorterDuff.Mode.SRC_IN);
        }
    }
}
