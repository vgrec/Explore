package com.traveler.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.traveler.Constants;
import com.traveler.Extra;
import com.traveler.R;
import com.traveler.activity.AttractionsActivity;
import com.traveler.activity.DescriptionActivity;
import com.traveler.activity.ImagesActivity;
import com.traveler.activity.LocationSummaryActivity;
import com.traveler.activity.PlaceDetailActivity;
import com.traveler.activity.VideosActivity;
import com.traveler.http.TravelerIoFacade;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.http.VolleySingleton;
import com.traveler.models.events.FlickrPhotosErrorEvent;
import com.traveler.models.flickr.Photo;
import com.traveler.models.flickr.Size;
import com.traveler.models.google.Place;
import com.traveler.models.google.PlaceItemsResponse;
import com.traveler.models.google.PlaceType;
import com.traveler.models.wikipedia.DescriptionResponse;
import com.traveler.models.wikipedia.PageDescription;
import com.traveler.models.youtube.Entry;
import com.traveler.models.youtube.Video;
import com.traveler.models.youtube.VideosResponse;
import com.traveler.utils.Utils;
import com.traveler.utils.VideoUtils;
import com.traveler.views.PreviewCard;
import com.traveler.views.PreviewImage;
import com.traveler.views.ScrimImageHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author vgrec, created on 8/22/14.
 */
public class LocationSummaryFragment extends Fragment {

    public static final String KEY_PAGE_DESCRIPTION = "KEY_PAGE_DESCRIPTION";

    private ArrayList<Photo> photos;
    private PageDescription pageDescription;
    private List<Place> places;
    private List<Video> videos;

    @InjectView(R.id.description)
    TextView descriptionTextView;

    @InjectView(R.id.location)
    TextView locationTextView;

    @InjectView(R.id.small_image)
    ImageView smallImageView;

    @InjectView(R.id.big_image_header)
    ScrimImageHeader scrimImageHeader;

    @InjectView(R.id.title_header)
    View titleHeader;

    @InjectView(R.id.description_card)
    PreviewCard descriptionCard;

    @InjectView(R.id.attractions_card)
    PreviewCard attractionsCard;

    @InjectView(R.id.videos_card)
    PreviewCard videosCard;

    @InjectView(R.id.separator_line)
    View separatorLine;

    @InjectView(R.id.videos_container)
    ViewGroup videosContainer;

    @InjectView(R.id.attractions_photos_container)
    ViewGroup placesContainer;

    public LocationSummaryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_summary, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.setColorForTextViewDrawable(getResources().getColor(R.color.colorPrimary), descriptionCard.getTitleTextView(),
                attractionsCard.getTitleTextView(), videosCard.getTitleTextView());
    }

    @OnClick(R.id.big_image)
    void openImagesActivity() {
        if (photos != null && photos.size() > 2) {
            Intent intent = new Intent(getActivity(), ImagesActivity.class);
            intent.putExtra(Extra.PHOTOS, Utils.flickrPhotosToUrls(photos));
            startActivity(intent);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListenersForPreviewCards();

        final TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity());

        if (getActivity() != null) {
            ((LocationSummaryActivity) getActivity()).getProgressView().setTryAgainClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressView();
                    ioFacade.getFlickrPhotos();
                    ioFacade.getPlaces(PlaceType.RESTAURANT, "");
                    ioFacade.getDescription();
                    ioFacade.getVideos();
                }
            });
        }

        if (photos == null) {
            showProgressView();
            ioFacade.getFlickrPhotos();
        } else {
            downloadFlickrPhotos();
        }

        if (places == null) {
            ioFacade.getPlaces(PlaceType.RESTAURANT, "");
        } else {
            showFirstPlaces();
        }

        if (pageDescription == null) {
            ioFacade.getDescription();
        } else {
            showLocationShortDescription();
        }

        if (videos == null) {
            ioFacade.getVideos();
        } else {
            showFirstVideos();
        }
    }

    private void setListenersForPreviewCards() {
        descriptionCard.setViewAllButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open description
                if (pageDescription != null) {
                    Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                    intent.putExtra(KEY_PAGE_DESCRIPTION, pageDescription);
                    startActivity(intent);
                }
            }
        });

        attractionsCard.setViewAllButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open attractions
                Intent intent = new Intent(getActivity(), AttractionsActivity.class);
                if (pageDescription != null) {
                    intent.putExtra(AttractionsActivity.TITLE, pageDescription.getTitle());
                }
                startActivity(intent);
            }
        });

        videosCard.setViewAllButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open videos
                Intent intent = new Intent(getActivity(), VideosActivity.class);
                startActivity(intent);
            }
        });
    }

    // On attractions received
    public void onEvent(PlaceItemsResponse result) {
        if (result != null && result.getPlaceType() == PlaceType.RESTAURANT) {
            places = new ArrayList<>();
            places.addAll(result.getPlaces());
            showFirstPlaces();
        }
    }

    // On wikipedia description received
    public void onEvent(DescriptionResponse result) {
        pageDescription = result.getPageDescription();
        showLocationShortDescription();
    }

    // On Flickr photos received
    public void onEvent(List<Photo> result) {
        if (result.size() > 2) {
            photos = new ArrayList<>();
            photos.addAll(result);
            downloadFlickrPhotos();
            // in case result is greater than 2, then the progress view is hidden after the
            // Palette generated the color for header title.
        } else {
            separatorLine.setVisibility(View.GONE);
            hideProgressView();
        }
    }

    // On youtube videos received
    public void onEvent(VideosResponse result) {
        if (result != null) {
            List<Entry> entries = result.getFeed().getEntries();
            videos = VideoUtils.toVideos(entries);
            showFirstVideos();
        }
    }


    public void onEvent(FlickrPhotosErrorEvent error) {
        showProgressViewError();
    }

    private void showLocationShortDescription() {
        descriptionTextView.setText(pageDescription.getExtract());
        locationTextView.setText(pageDescription.getTitle());

        // if there's some description, then show the card
        if (pageDescription.getExtract() != null && pageDescription.getExtract().length() > 0) {
            descriptionCard.setVisibility(View.VISIBLE);
        }
    }

    private void downloadFlickrPhotos() {
        if (photos != null) {
            scrimImageHeader.setNumberOfPhotos(photos.size());
            downloadImage(photos.get(0), Size.z);
            downloadImageAndProcessColor(photos.get(1), smallImageView, Size.q);
        } else {
            hideProgressView();
        }
    }

    private void showFirstPlaces() {
        int numberOfImages = placesContainer.getChildCount();
        if (places == null || places.size() < numberOfImages) {
            return;
        }
        attractionsCard.setVisibility(View.VISIBLE);

        for (int i = 0; i < numberOfImages; i++) {
            PreviewImage previewImage = (PreviewImage) placesContainer.getChildAt(i);
            final int finalI = i;
            previewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlaceDetailsActivity(places.get(finalI));
                }
            });
            if (places.get(i).getPhotos() != null && places.get(i).getPhotos().size() > 0) {
                String url = String.format(Constants.Google.THUMBNAIL_URL, places.get(i).getPhotos().get(0).getPhotoReference());
                previewImage.setUrl(url);
            } else {
                previewImage.setUrl(places.get(i).getIconUrl());
            }
            previewImage.setText(places.get(i).getName());
        }
    }

    private void openPlaceDetailsActivity(Place place) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(Extra.PLACE_ID, place.getPlaceId());
        startActivity(intent);
    }

    private void showFirstVideos() {
        int numberOfImages = videosContainer.getChildCount();
        if (videos.size() < numberOfImages) {
            return;
        }
        videosCard.setVisibility(View.VISIBLE);

        for (int i = 0; i < numberOfImages; i++) {
            PreviewImage previewImage = (PreviewImage) videosContainer.getChildAt(i);
            final int finalI = i;
            previewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(finalI).getLink())));
                }
            });
            previewImage.setUrl(videos.get(i).getThumbnailUrl());
            previewImage.setText(videos.get(i).getTitle());
        }
    }


    private void downloadImageAndProcessColor(Photo photo, final ImageView imageView, Size size) {
        com.android.volley.toolbox.ImageLoader imageLoader = VolleySingleton.getInstance(getActivity()).getImageLoader();
        String url = String.format(Constants.Flickr.PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), size);
        imageLoader.get(url, new com.android.volley.toolbox.ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.ic_launcher);
                hideProgressView();
            }

            public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer response, boolean arg1) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {
                    smallImageView.setImageBitmap(bitmap);
                    generatePaletteAndSetColor(bitmap);
                }
//                else {
//                    hideProgressView();
//                }
            }
        });
    }

    private void generatePaletteAndSetColor(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                if (getActivity() != null) {
                    TravelerIoFacadeImpl.TravelerSettings settings = TravelerIoFacadeImpl.TravelerSettings.getInstance(getActivity());
                    settings.setDarkMutedColor(palette.getDarkVibrantColor(getResources().getColor(R.color.dark_grey)));

                }
                titleHeader.setBackgroundColor(palette.getDarkVibrantColor(getResources().getColor(R.color.dark_grey)));
                hideProgressView();
            }
        });
    }

    private void downloadImage(Photo photo, Size size) {
        String url = String.format(Constants.Flickr.PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), size);
        scrimImageHeader.setImageUrl(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void hideProgressView() {
        if (getActivity() != null) {
            ((LocationSummaryActivity) getActivity()).getProgressView().hide();
        }
    }

    private void showProgressViewError() {
        if (getActivity() != null) {
            ((LocationSummaryActivity) getActivity()).getProgressView().showError();
        }
    }

    private void showProgressView() {
        if (getActivity() != null) {
            ((LocationSummaryActivity) getActivity()).getProgressView().show();
        }
    }
}
