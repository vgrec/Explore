package com.traveler.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.traveler.Constants;
import com.traveler.Extra;
import com.traveler.ImageHelper;
import com.traveler.PlaceType;
import com.traveler.R;
import com.traveler.Size;
import com.traveler.activity.AttractionsActivity;
import com.traveler.activity.DescriptionActivity;
import com.traveler.activity.ImagesActivity;
import com.traveler.activity.PlaceDetailActivity;
import com.traveler.activity.StreetViewActivity;
import com.traveler.activity.VideosActivity;
import com.traveler.http.TaskFinishedListener;
import com.traveler.http.TravelerIoFacade;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.http.VolleySingleton;
import com.traveler.models.flickr.Photo;
import com.traveler.models.google.Place;
import com.traveler.models.google.PlaceItemsResponse;
import com.traveler.models.wikipedia.DescriptionResponse;
import com.traveler.models.wikipedia.PageDescription;
import com.traveler.models.youtube.Entry;
import com.traveler.models.youtube.Video;
import com.traveler.models.youtube.VideosResponse;
import com.traveler.utils.ScrimUtil;
import com.traveler.utils.Utils;
import com.traveler.utils.VideoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author vgrec, created on 8/22/14.
 */
public class LocationSummaryFragment extends Fragment {

    public static final String KEY_LOCATION = "KEY_LOCATION";
    public static final String PAGE_DESCRIPTION = "PAGE_DESCRIPTION";

    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private PageDescription pageDescription;
    private String location;
    private int vibrantColor;

    private int tasksFinished;
    private int totalNumberOfTasks = 4;

    @InjectView(R.id.description)
    TextView descriptionTextView;

    @InjectView(R.id.location)
    TextView locationTextView;

    @InjectView(R.id.big_image)
    NetworkImageView bigImageView;

    @InjectView(R.id.small_image)
    ImageView smallImageView;

    @InjectView(R.id.title_header)
    View titleHeader;

    @InjectView(R.id.description_label)
    TextView descriptionLabel;

    @InjectView(R.id.attractions_label)
    TextView attractionsLabel;

    @InjectView(R.id.streetview_label)
    TextView streetViewLabel;

    @InjectView(R.id.videos_label)
    TextView videosLabel;

    @InjectView(R.id.header_container)
    ViewGroup headerContainer;

    @InjectView(R.id.progress_view)
    ViewGroup progressView;

    @InjectView(R.id.videos_container)
    ViewGroup videosContainer;

    @InjectView(R.id.attractions_photos_container)
    ViewGroup placesContainer;

    public LocationSummaryFragment() {
    }

    public static LocationSummaryFragment newInstance(String location) {
        Bundle args = new Bundle();
        args.putString(KEY_LOCATION, location);

        LocationSummaryFragment fragment = new LocationSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = getArguments().getString(KEY_LOCATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_summary, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.attractions_card)
    void openAttractionsActivity() {
        Intent intent = new Intent(getActivity(), AttractionsActivity.class);
        intent.putExtra(Extra.LOCATION, location);
        intent.putExtra(Extra.VIBRANT_COLOR, vibrantColor);
        startActivity(intent);
    }

    @OnClick(R.id.streetview_card)
    void openStreetViewActivity() {
        Intent intent = new Intent(getActivity(), StreetViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.videos_card)
    void openVideosActivity() {
        Intent intent = new Intent(getActivity(), VideosActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.description)
    void openDescriptionActivity() {
        if (pageDescription != null) {
            Intent intent = new Intent(getActivity(), DescriptionActivity.class);
            intent.putExtra(PAGE_DESCRIPTION, pageDescription);
            startActivity(intent);
        }
    }

    @OnClick(R.id.big_image)
    void openImagesActivity() {
        if (photos.size() > 2) {
            Intent intent = new Intent(getActivity(), ImagesActivity.class);
            intent.putExtra(Extra.PHOTOS, photos);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getAttractions();
        getFlickrPhotos();
        getDescription();
        getVideos();

        headerContainer.setBackgroundDrawable(ScrimUtil.makeCubicGradientScrimDrawable(0xaa000000, 8, Gravity.BOTTOM));
    }

    private void getAttractions() {
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity()).forLocation(location);
        ioFacade.getPlaces(new TaskFinishedListener<PlaceItemsResponse>() {
            @Override
            protected void onSuccess(PlaceItemsResponse result) {
                if (result != null) {
                    showFirstPlaces(result.getPlaces());
                }
            }
        }, PlaceType.RESTAURANT);
    }

    private void showFirstPlaces(final List<Place> places) {
        int numberOfImages = placesContainer.getChildCount();
        if (places.size() < numberOfImages) {
            // TODO:// hide attractions card
            return;
        }

        for (int i = 0; i < numberOfImages; i++) {
            ViewGroup viewGroup = (ViewGroup) placesContainer.getChildAt(i);
            NetworkImageView imageView = (NetworkImageView) viewGroup.getChildAt(0);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlaceDetailsActivity(places.get(finalI));
                }
            });
            if (places.get(i).getPhotos() != null && places.get(i).getPhotos().size() > 0) {
                String url = String.format(Constants.Google.THUMBNAIL_URL, places.get(i).getPhotos().get(0).getPhotoReference());
                ImageHelper.loadImage(getActivity(), url, imageView);
            } else {
                ImageHelper.loadImage(getActivity(), places.get(i).getIconUrl(), imageView);
            }
            TextView textView = (TextView) viewGroup.getChildAt(1);
            textView.setText(places.get(i).getName());
        }
    }

    private void openPlaceDetailsActivity(Place place) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(Extra.PLACE_ID, place.getPlaceId());
        intent.putExtra(Extra.VIBRANT_COLOR, vibrantColor);
        startActivity(intent);
    }

    private void getFlickrPhotos() {
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity()).forLocation(location);
        ioFacade.getPhotos(new TaskFinishedListener<List<Photo>>() {
            @Override
            protected void onSuccess(List<Photo> result) {
                if (result.size() > 2) {
                    photos.addAll(result);
                    downloadImage(result.get(0), bigImageView, Size.z);
                    downloadImageAndProcessColor(result.get(1), smallImageView, Size.q);
                } else {
                    checkTasks();
                }
            }

            @Override
            protected void onFailure(VolleyError error) {
                checkTasks();
            }
        });
    }

    private void checkTasks() {
        tasksFinished++;
        if (tasksFinished == totalNumberOfTasks) {
            progressView.setVisibility(View.GONE);
        }
    }

    private void getVideos() {
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity()).forLocation(location);
        ioFacade.getVideos(new TaskFinishedListener<VideosResponse>() {
            @Override
            protected void onSuccess(VideosResponse result) {
                if (result != null) {
                    List<Entry> entries = result.getFeed().getEntries();
                    List<Video> videos = VideoUtils.toVideos(entries);
                    showFirstVideos(videos);
                }
                checkTasks();
            }

            @Override
            protected void onFailure(VolleyError error) {
                checkTasks();
            }
        });
    }

    private void showFirstVideos(final List<Video> videos) {
        int numberOfImages = videosContainer.getChildCount();
        if (videos.size() < numberOfImages) {
            // TODO: hide videos card
            return;
        }

        for (int i = 0; i < numberOfImages; i++) {
            ViewGroup viewGroup = (ViewGroup) videosContainer.getChildAt(i);
            NetworkImageView imageView = (NetworkImageView) viewGroup.getChildAt(0);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(finalI).getLink())));
                }
            });
            ImageHelper.loadImage(getActivity(), videos.get(i).getThumbnailUrl(), imageView);
            TextView textView = (TextView) viewGroup.getChildAt(1);
            textView.setText(videos.get(i).getTitle());
        }
    }

    private void getDescription() {
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity()).forLocation(location);
        ioFacade.getDescription(new TaskFinishedListener<DescriptionResponse>() {
            @Override
            protected void onSuccess(DescriptionResponse result) {
                pageDescription = result.getPageDescription();

                descriptionTextView.setText(result.getPageDescription().getExtract());
                locationTextView.setText(result.getPageDescription().getTitle());
                checkTasks();
            }

            @Override
            protected void onFailure(VolleyError error) {
                checkTasks();
            }
        });
    }

    private void downloadImageAndProcessColor(Photo photo, final ImageView imageView, Size size) {
        ImageLoader imageLoader = VolleySingleton.getInstance(getActivity()).getImageLoader();
        String url = String.format(Constants.Flickr.PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), size);
        imageLoader.get(url, new ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.ic_launcher);
                checkTasks();
            }

            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {
                    smallImageView.setImageBitmap(bitmap);
                    generatePaletteAndSetColor(bitmap);
                } else {
                    checkTasks();
                }
            }
        });
    }

    private void generatePaletteAndSetColor(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                vibrantColor = palette.getDarkMutedColor(Color.DKGRAY);
                titleHeader.setBackgroundColor(vibrantColor);
                Utils.setColorForTextViewDrawable(vibrantColor, descriptionLabel, streetViewLabel, attractionsLabel, videosLabel);
                checkTasks();
            }
        });
    }

    private void downloadImage(Photo photo, NetworkImageView targetImageView, Size size) {
        String url = String.format(Constants.Flickr.PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), size);
        ImageHelper.loadImage(getActivity(), url, targetImageView);
    }
}
