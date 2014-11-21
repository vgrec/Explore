package com.traveler.fragment;

import android.app.Fragment;
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
public class PlaceDetailFragment extends Fragment {

    private String placeId;
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

    public static Fragment newInstance(String placeId) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putString(Extra.PLACE_ID, placeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            placeId = getArguments().getString(Extra.PLACE_ID);
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
        nameTextView.setText(place.getName());
        addressTextView.setText(place.getAddress());
        phoneNumberTextView.setText(place.getPhoneNumber());
        ratingTextView.setText(place.getRating());
        webSiteTextView.setText(place.getWebSite());

        if (place.getPhotos().size() > 0) {
            String url = String.format(Constants.Google.IMAGE_URL, place.getPhotos().get(0).getPhotoReference());
            ImageHelper.loadImage(getActivity(), url, placeDetailImageView);
        }

    }
}
