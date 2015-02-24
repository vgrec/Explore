package com.traveler.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.traveler.Constants;
import com.traveler.Extra;
import com.traveler.R;
import com.traveler.activity.ImagesActivity;
import com.traveler.activity.PlaceDetailActivity;
import com.traveler.db.SavedPlacesDataSource;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.models.db.SavedPlace;
import com.traveler.models.events.PlaceDetailsErrorEvent;
import com.traveler.models.google.Place;
import com.traveler.models.google.PlaceDetailsResponse;
import com.traveler.models.google.Review;
import com.traveler.utils.AnimationUtils;
import com.traveler.utils.Utils;
import com.traveler.views.ScrimImageHeader;
import com.traveler.views.SmartTextView;

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
    SmartTextView addressTextView;

    @InjectView(R.id.phone_number)
    SmartTextView phoneNumberTextView;

    @InjectView(R.id.rating)
    SmartTextView ratingTextView;

    @InjectView(R.id.web_site)
    SmartTextView webSiteTextView;

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
        TravelerIoFacadeImpl ioFacade = new TravelerIoFacadeImpl(getActivity());
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

        int primaryColor = TravelerIoFacadeImpl.TravelerSettings.getInstance(getActivity()).getPrimaryColor();

        nameTextView.setText(place.getName());
        detailsHeaderContainer.setBackgroundColor(primaryColor);
        ratingTextView.setText(place.getRating());
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
