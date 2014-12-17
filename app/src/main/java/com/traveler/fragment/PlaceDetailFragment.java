package com.traveler.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
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
import com.traveler.models.google.Review;
import com.traveler.utils.Utils;
import com.traveler.views.ScrimImageHeader;

import java.util.List;

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

    @InjectView(R.id.big_image_header)
    ScrimImageHeader bigImageHeader;

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

    @InjectView(R.id.reviews_container)
    ViewGroup reviewsContainer;

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

        Utils.setColorForTextViewDrawable(vibrantColor, addressTextView, phoneNumberTextView, webSiteTextView);

        if (place.getPhotos().size() > 0) {
            String url = String.format(Constants.Google.IMAGE_URL, place.getPhotos().get(0).getPhotoReference());
            bigImageHeader.setImageUrl(url);
            bigImageHeader.setNumberOfPhotos(place.getPhotos().size());
        }

        displayReviews(place.getReviews());
    }

    private void displayReviews(List<Review> reviews) {
        for (Review review : reviews) {
            View row = View.inflate(getActivity(), R.layout.layout_review, null);
            if (row != null) {
                TextView author = (TextView) row.findViewById(R.id.author);
                author.setText(review.getAuthor());
                TextView text = (TextView) row.findViewById(R.id.text);
                text.setText(review.getText());
                RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating_bar);
                ratingBar.setNumStars(Integer.valueOf(review.getRating()));
                reviewsContainer.addView(row);
            }
        }
    }

//    Intent intent = new Intent(Intent.ACTION_DIAL);
//    intent.setData(Uri.parse("1234567890"))
//    startActivity(intent);
}
