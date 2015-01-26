package com.traveler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.Extra;
import com.traveler.R;
import com.traveler.activity.PlaceDetailActivity;
import com.traveler.adapters.SavedPlacesAdapter;
import com.traveler.db.SavedPlacesDataSource;
import com.traveler.listeners.RecyclerItemClickListener;
import com.traveler.models.db.SavedPlace;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritePlacesFragment extends Fragment {

    private List<SavedPlace> savedPlaces = new ArrayList<>();

    @InjectView(R.id.favorites)
    RecyclerView recyclerView;


    public FavoritePlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.favorite_places);
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

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns)));
        SavedPlacesAdapter adapter = new SavedPlacesAdapter(savedPlaces);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openPlaceDetailsActivity(savedPlaces.get(position));
                    }
                })
        );

        SavedPlacesDataSource dataSource = new SavedPlacesDataSource(getActivity());
        dataSource.open();

        savedPlaces.clear();
        savedPlaces.addAll(dataSource.getPlaces());
        adapter.notifyDataSetChanged();

        dataSource.close();
    }

    private void openPlaceDetailsActivity(SavedPlace savedPlace) {
        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(Extra.PLACE_ID, savedPlace.getPlaceId());
        startActivity(intent);
    }
}
