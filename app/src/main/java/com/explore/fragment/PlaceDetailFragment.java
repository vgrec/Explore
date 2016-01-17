package com.explore.fragment;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.Constants;
import com.explore.Extra;
import com.vgrec.explore.R;
import com.explore.activity.ImagesActivity;
import com.explore.activity.MapActivity;
import com.explore.activity.PlaceDetailActivity;
import com.explore.db.SavedPlacesDataSource;
import com.explore.http.ExploreHttpFacadeImpl;
import com.explore.models.db.SavedPlace;
import com.explore.models.events.PlaceDetailsErrorEvent;
import com.explore.models.google.Place;
import com.explore.models.google.PlaceDetailsResponse;
import com.explore.models.google.Review;
import com.explore.utils.AnimationUtils;
import com.explore.utils.Utils;
import com.explore.views.ScrimImageHeader;
import com.explore.views.SmartTextView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Shows detailed information about a specific place
 *
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

    @InjectView(R.id.reviews_title)
    TextView reviewsTextView;

    @InjectView(R.id.place_address)
    SmartTextView addressTextView;

    @InjectView(R.id.phone_number)
    SmartTextView phoneNumberTextView;

    @InjectView(R.id.web_site)
    SmartTextView webSiteTextView;

    @InjectView(R.id.rating_bar)
    RatingBar placeRatingBar;

    @InjectView(R.id.details_header_container)
    ViewGroup detailsHeaderContainer;

    @InjectView(R.id.reviews_container)
    ViewGroup reviewsContainer;

    @InjectView(R.id.add_to_favorites)
    FloatingActionButton floatingActionButton;

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

        if (getActivity() != null) {
            ((PlaceDetailActivity) getActivity()).getProgressView().setTryAgainClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadPlaceDetailsAndUpdateUi();
                }
            });
        }
    }

    @OnClick(R.id.place_address)
    void showPlaceAddressOnMap() {
        if (placeResponse != null && placeResponse.getPlace() != null && placeResponse.getPlace().getGeometry() != null) { // this is java.
            String lat = placeResponse.getPlace().getGeometry().getLocation().getLat();
            String lng = placeResponse.getPlace().getGeometry().getLocation().getLng();
            String address = placeResponse.getPlace().getAddress();

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putExtra(ExploreMapFragment.KEY_LATITUDE, lat);
            intent.putExtra(ExploreMapFragment.KEY_LONGITUDE, lng);
            intent.putExtra(ExploreMapFragment.KEY_ADDRESS, address);
            startActivity(intent);
        }
    }

    @OnClick(R.id.phone_number)
    void openDialNumberScreen() {

        // for example tablets don't have telephony
        if (!isTelephonySupported()) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + placeResponse.getPlace().getPhoneNumber().replaceAll(" ", "")));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Could not find any activity that takes an ACTION_DIAL intent.
            // Do nothing.
        }
    }

    private boolean isTelephonySupported() {
        return getActivity() != null && getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }


    @OnClick(R.id.web_site)
    void openPlaceWebSite() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeResponse.getPlace().getWebSite()));
            startActivity(browserIntent);
        } catch (Exception e) {
            // just in case the supplied address from the server is not valid.
        }
    }

    @OnClick(R.id.big_image_header)
    void openImagesActivity() {
        if (placeResponse == null) {
            return;
        }

        Place place = placeResponse.getPlace();
        if (place == null) {
            return;
        }

        if (place.getPhotos().size() > 0) {
            Intent intent = new Intent(getActivity(), ImagesActivity.class);
            intent.putExtra(Extra.PHOTOS, Utils.googlePhotosToUrls(place.getPhotos()));
            startActivity(intent);
        }
    }


    @OnClick(R.id.add_to_favorites)
    void addOrRemovePlaceFromFavorites() {
        if (placeResponse != null) {
            Place place = placeResponse.getPlace();
            if (place != null) {
                SavedPlacesDataSource dataSource = new SavedPlacesDataSource(getActivity());
                dataSource.open();
                if (dataSource.isPlaceSaved(placeId)) {
                    dataSource.removePlace(placeId);
                    animateRemoveFromFavorites();
                    Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    SavedPlace savedPlace = new SavedPlace();
                    savedPlace.setPlaceId(placeId);
                    savedPlace.setTitle(place.getName());
                    savedPlace.setImageUrl(place.getPhotos().size() > 0 ? place.getPhotos().get(0).getPhotoReference() : null);
                    dataSource.savePlace(savedPlace);
                    animateAddToFavorites();
                    Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                dataSource.close();
            }
        }
    }

    private void animateRemoveFromFavorites() {
        AnimationUtils.bounceAnimation(floatingActionButton, R.drawable.ic_heart_outline_grey);
    }

    private void animateAddToFavorites() {
        AnimationUtils.bounceAnimation(floatingActionButton, R.drawable.ic_heart_red);
    }

    public void onEvent(PlaceDetailsResponse result) {
        hideProgressView();
        placeResponse = result;
        updateUi();
    }

    public void onEvent(PlaceDetailsErrorEvent error) {
        showProgressViewError();
    }

    private void downloadPlaceDetailsAndUpdateUi() {
        showProgressView();
        ExploreHttpFacadeImpl ioFacade = new ExploreHttpFacadeImpl(getActivity());
        ioFacade.getPlaceDetails(placeId);
    }

    private void hideProgressView() {
        if (getActivity() != null) {
            ((PlaceDetailActivity) getActivity()).getProgressView().hide();
        }
    }

    private void showProgressViewError() {
        if (getActivity() != null) {
            ((PlaceDetailActivity) getActivity()).getProgressView().showError();
        }
    }

    private void showProgressView() {
        if (getActivity() != null) {
            ((PlaceDetailActivity) getActivity()).getProgressView().show();
        }
    }


    private void updateUi() {
        Place place = placeResponse.getPlace();
        if (place == null) {
            return;
        }

        updateAddToFavoritesIcon(place.getPlaceId());

        int primaryColor = ExploreHttpFacadeImpl.InternalExploreSettings.getInstance(getActivity()).getPrimaryColor();

        nameTextView.setText(place.getName());
        detailsHeaderContainer.setBackgroundColor(primaryColor);

        if (place.getRating() != null) {
            placeRatingBar.setRating(Float.valueOf(place.getRating()));
            placeRatingBar.setVisibility(View.VISIBLE);
        }

        addressTextView.setText(place.getAddress());
        phoneNumberTextView.setText(place.getPhoneNumber());
        webSiteTextView.setText(place.getWebSite());

        Utils.setColorForTextViewDrawable(getResources().getColor(R.color.colorPrimary), addressTextView, phoneNumberTextView, webSiteTextView);

        if (place.getPhotos().size() > 0) {
            String url = String.format(Constants.Google.IMAGE_URL, place.getPhotos().get(0).getPhotoReference());
            bigImageHeader.setImageUrl(url);
            bigImageHeader.setNumberOfPhotos(place.getPhotos().size());
        }

        displayReviews(place.getReviews());
    }

    private void updateAddToFavoritesIcon(String placeId) {
        SavedPlacesDataSource dataSource = new SavedPlacesDataSource(getActivity());
        dataSource.open();
        if (dataSource.isPlaceSaved(placeId)) {
            floatingActionButton.setImageResource(R.drawable.ic_heart_red);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_heart_outline_grey);
        }
        dataSource.close();
    }

    private void displayReviews(List<Review> reviews) {
        if (reviews.size() > 0) {
            reviewsTextView.setVisibility(View.VISIBLE);
        }

        for (Review review : reviews) {
            View row = View.inflate(getActivity(), R.layout.layout_review, null);
            if (row != null) {
                TextView author = (TextView) row.findViewById(R.id.author);
                author.setText(review.getAuthor());
                TextView text = (TextView) row.findViewById(R.id.text);
                text.setText(review.getText());
                RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating_bar);
                ratingBar.setRating(Float.valueOf(review.getRating()));
                reviewsContainer.addView(row);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
