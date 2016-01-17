package com.explore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.explore.Extra;
import com.explore.listeners.OnItemClickListener;
import com.vgrec.explore.R;
import com.explore.activity.MainActivity;
import com.explore.activity.PlaceDetailActivity;
import com.explore.adapters.SavedPlacesAdapter;
import com.explore.db.SavedPlacesDataSource;
import com.explore.models.db.SavedPlace;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SavedPlacesFragment extends Fragment {

    private List<SavedPlace> savedPlaces = new ArrayList<>();
    SavedPlacesAdapter adapter = new SavedPlacesAdapter(savedPlaces);

    @InjectView(R.id.favorites)
    RecyclerView recyclerView;

    @InjectView(R.id.empty_view)
    TextView emptyView;


    public SavedPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setTitle(R.string.favorite_places);
            ((MainActivity) getActivity()).setCheckedItem(1);
            displaySavedPlaces();
        }
    }

    private void displaySavedPlaces() {
        SavedPlacesDataSource dataSource = new SavedPlacesDataSource(getActivity());
        dataSource.open();

        List<SavedPlace> places = dataSource.getPlaces();
        if (places.size() <= 0) {
            emptyView.setVisibility(View.VISIBLE);
        }

        savedPlaces.clear();
        savedPlaces.addAll(places);
        adapter.notifyDataSetChanged();

        dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_places, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setOnItemClickListener(new OnItemClickListener<SavedPlace>() {
            @Override
            public void onItemClick(SavedPlace item) {
                openPlaceDetailsActivity(item);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns)));
        recyclerView.setAdapter(adapter);
    }

    private void openPlaceDetailsActivity(SavedPlace savedPlace) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(Extra.PLACE_ID, savedPlace.getPlaceId());
        startActivity(intent);
    }
}
