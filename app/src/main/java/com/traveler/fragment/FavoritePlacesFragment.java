package com.traveler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.R;
import com.traveler.adapters.FavoriteLocationsAdapter;
import com.traveler.db.LocationsDataSource;
import com.traveler.listeners.RecyclerItemClickListener;
import com.traveler.models.db.Location;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritePlacesFragment extends Fragment {

    private List<Location> locations = new ArrayList<>();

    @InjectView(R.id.favorites)
    RecyclerView recyclerView;


    public FavoritePlacesFragment() {
        // Required empty public constructor
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
        FavoriteLocationsAdapter adapter = new FavoriteLocationsAdapter(locations);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openPlaceDetailsActivity(locations.get(position));
                    }
                })
        );

        LocationsDataSource dataSource = new LocationsDataSource(getActivity());
        dataSource.open();

        locations.clear();
        locations.addAll(dataSource.getLocalities());
        adapter.notifyDataSetChanged();

        dataSource.close();
    }

    private void openPlaceDetailsActivity(Location location) {

    }


}
