package com.traveler.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.traveler.Constants;
import com.traveler.Extra;
import com.traveler.R;
import com.traveler.db.SavedPlacesDataSource;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.models.db.SavedPlace;
import com.traveler.models.events.PlaceDetailsErrorEvent;
import com.traveler.models.google.Place;
import com.traveler.models.google.PlaceDetailsResponse;
import com.traveler.models.google.Review;
import com.traveler.utils.Utils;
import com.traveler.views.ProgressView;
import com.traveler.views.ScrimImageHeader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author vgrec, created on 11/17/14.
 */

// http://blog.syedgakbar.com/2012/07/changing-color-of-the-drawable-or-imageview-at-runtime-in-android/
public class PlaceDetailFragment extends Fragment {

    private String placeId;
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

    @InjectView(R.id.progress_view)
    ProgressView progressView;

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
            EventBus.getDefault().register(this);
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

        progressView.setTryAgainClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPlaceDetailsAndUpdateUi();
            }
        });
    }

    @OnClick(R.id.add_to_favorites)
    void addPlaceToFavorites() {
        if (placeResponse != null) {
            Place place = placeResponse.getPlace();
            if (place != null) {
                SavedPlace savedPlace = new SavedPlace();
                savedPlace.setPlaceId(placeId);
                savedPlace.setTitle(place.getName());
                savedPlace.setImageUrl(place.getPhotos().size() > 0 ? place.getPhotos().get(0).getPhotoReference() : null);

                SavedPlacesDataSource dataSource = new SavedPlacesDataSource(getActivity());
                dataSource.open();
                dataSource.savePlace(savedPlace);
                dataSource.close();
            }
        }
    }


    public void onEvent(PlaceDetailsResponse result) {
        progressView.hide();
        placeResponse = result;
        updateUi();
    }

    public void onEvent(PlaceDetailsErrorEvent error) {
        progressView.showError();
    }

    private void downloadPlaceDetailsAndUpdateUi() {
        progressView.show();
        TravelerIoFacadeImpl ioFacade = new TravelerIoFacadeImpl(getActivity());
        ioFacade.getPlaceDetails(placeId);
    }

    private void updateUi() {
        Place place = placeResponse.getPlace();
        if (place == null) {
            return;
        }

        int primaryColor = TravelerIoFacadeImpl.TravelerSettings.getInstance(getActivity()).getPrimaryColor();

        nameTextView.setText(place.getName());
        detailsHeaderContainer.setBackgroundColor(primaryColor);
        addressTextView.setText(place.getAddress());
        phoneNumberTextView.setText(place.getPhoneNumber());
        ratingTextView.setText(place.getRating());
        webSiteTextView.setText(place.getWebSite());

        Utils.setColorForTextViewDrawable(getResources().getColor(R.color.colorPrimary), addressTextView, phoneNumberTextView, webSiteTextView);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //    Intent intent = new Intent(Intent.ACTION_DIAL);
//    intent.setData(Uri.parse("1234567890"))
//    startActivity(intent);
}
