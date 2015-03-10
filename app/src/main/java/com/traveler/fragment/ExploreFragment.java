package com.traveler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.traveler.R;
import com.traveler.activity.LocationSummaryActivity;
import com.traveler.activity.MainActivity;
import com.traveler.adapters.PlacesAutoCompleteAdapter;
import com.traveler.http.TravelerIoFacadeImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ExploreFragment extends Fragment {

    @InjectView(R.id.go)
    Button goButton;

    @InjectView(R.id.location_autocomplete)
    AutoCompleteTextView autoCompleteView;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setTitle(R.string.app_name);
            ((MainActivity)getActivity()).setCheckedItem(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: for testing
//        goButton.setEnabled(false);

        autoCompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.item_autocomplete));
        autoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String location = (String) adapterView.getItemAtPosition(position);
                startSummaryActivity(location);
            }
        });
    }

    @OnClick(R.id.go)
    void startLocationSummaryActivity() {
        String location = autoCompleteView.getText().toString().trim();
        startSummaryActivity(location);
    }

    private void startSummaryActivity(String location) {
        TravelerIoFacadeImpl.TravelerSettings.getInstance(getActivity()).setLocation(location);
        Intent intent = new Intent(getActivity(), LocationSummaryActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(R.id.location_autocomplete)
    void onTextChanged(CharSequence charSequence) {
        goButton.setEnabled(charSequence.length() > 0);
    }
}
