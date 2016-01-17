package com.explore.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.Extra;
import com.explore.listeners.OnItemClickListener;
import com.vgrec.explore.R;
import com.explore.activity.PlaceDetailActivity;
import com.explore.adapters.PlaceItemAdapter;
import com.explore.http.ExploreHttpFacade;
import com.explore.http.ExploreHttpFacadeImpl;
import com.explore.models.events.AttractionsErrorEvent;
import com.explore.models.google.Place;
import com.explore.models.google.PlaceItemsResponse;
import com.explore.models.google.PlaceType;
import com.explore.views.ProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Displays a list of attractions, eg.: Restaurants, Museums, etc,
 * retrieved from Google Places.
 *
 * @author vgrec, created on 11/3/14.
 */
public class AttractionsFragment extends Fragment {

    public static final String KEY_PLACE_TYPE = "KEY_PLACE_TYPE";
    private final int NUMBER_OF_ITEMS_PER_PAGE = 20;

    private PlaceItemAdapter adapter;
    private PlaceType placeType;
    private List<Place> places = new ArrayList<Place>();
    private int lastResultsSize;
    private String nextPageToken = "";
    /**
     * Whether the data we are viewing is first, or gathered by subsequently requests to onLoadMore.
     */
    private boolean dataLoadedFirstTime;

    @InjectView(R.id.attractions_list)
    RecyclerView recyclerView;

    @InjectView(R.id.progress_view)
    ProgressView progressView;

    public static AttractionsFragment newInstance(PlaceType placeType) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_PLACE_TYPE, placeType);
        AttractionsFragment fragment = new AttractionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AttractionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        placeType = (PlaceType) getArguments().getSerializable(KEY_PLACE_TYPE);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attractions, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new PlaceItemAdapter(places);
            downloadPlaces();
        }

        adapter.setLoadMoreItemListener(new PlaceItemAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (lastResultsSize >= NUMBER_OF_ITEMS_PER_PAGE) {
                    downloadPlaces();
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener<Place>() {
            @Override
            public void onItemClick(Place item) {
                openPlaceDetailsActivity(item);
            }
        });
        recyclerView.setAdapter(adapter);

        progressView.setTryAgainClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPlaces();
            }
        });
    }

    private void openPlaceDetailsActivity(Place position) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(Extra.PLACE_ID, position.getPlaceId());
        startActivity(intent);
    }

    private void downloadPlaces() {
        showTaskInProgress();
        ExploreHttpFacade ioFacade = new ExploreHttpFacadeImpl(getActivity());
        ioFacade.getPlaces(placeType, nextPageToken);
    }

    // On attractions received
    public void onEvent(PlaceItemsResponse result) {
        if (result != null) {
            if (result.getPlaceType() != placeType) {
                return;
            }
            hideTaskCompleted();
            nextPageToken = result.getNextPageToken();
            lastResultsSize = result.getPlaces().size();
            places.addAll(result.getPlaces());
            adapter.notifyDataSetChanged();
        }
    }

    public void onEvent(AttractionsErrorEvent error) {
        adapter.setTaskInProgress(false);
        progressView.showError();
    }

    private void showTaskInProgress() {
        adapter.setTaskInProgress(true);
        if (!dataLoadedFirstTime) {
            progressView.show();
        } else {
            if (getActivity() != null) {
                getActivity().setProgressBarIndeterminateVisibility(true);
            }
        }
    }

    private void hideTaskCompleted() {
        adapter.setTaskInProgress(false);
        if (!dataLoadedFirstTime) {
            progressView.hide();
            dataLoadedFirstTime = true;
        } else {
            if (getActivity() != null) {
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
