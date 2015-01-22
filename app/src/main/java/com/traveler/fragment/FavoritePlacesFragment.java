package com.traveler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traveler.R;
import com.traveler.db.LocationsDataSource;
import com.traveler.models.db.Location;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FavoritePlacesFragment extends Fragment {

    @InjectView(R.id.favorite_places)
    TextView locationsTextView;

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

        // testing

        Location l1 = new Location();
        l1.setTitle("Germany");
        l1.setType(Location.Type.LOCALITY);
        l1.setImageUrl("http://bff.com");

        Location l2 = new Location();
        l2.setTitle("Chisinau");
        l2.setType(Location.Type.LOCALITY);
        l2.setImageUrl("http://chisinau.com");

        Location l3 = new Location();
        l3.setTitle("Restaurant Bovarie");
        l3.setType(Location.Type.PLACE);
        l3.setImageUrl("http://bovarie.com");

        LocationsDataSource dataSource = new LocationsDataSource(getActivity());
        dataSource.open();
        dataSource.addLocation(l1);
        dataSource.addLocation(l2);
        dataSource.addLocation(l3);
        dataSource.close();
    }

    @OnClick(R.id.show)
    void onClick() {
        LocationsDataSource dataSource = new LocationsDataSource(getActivity());
        dataSource.open();
        List<Location> localities = dataSource.getLocalities();
        List<Location> places = dataSource.getPlaces();

        StringBuilder sb = new StringBuilder();

        sb.append("LOCALITIES:\n");
        for (Location location : localities) {
            sb.append(location.getTitle() + "\n");
            sb.append(location.getImageUrl() + "\n");
            sb.append(location.getType().toString() + "\n");
        }

        sb.append("\nPLACES:\n");
        for (Location location : places) {
            sb.append(location.getTitle() + "\n");
            sb.append(location.getImageUrl() + "\n");
            sb.append(location.getType().toString() + "\n");
        }
        dataSource.close();

        locationsTextView.setText(sb.toString());
    }
}
