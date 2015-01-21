package com.traveler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.traveler.R;
import com.traveler.activity.LocationSummaryActivity;
import com.traveler.http.TravelerIoFacadeImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ExploreFragment extends Fragment {

    @InjectView(R.id.go)
    Button goButton;

    @InjectView(R.id.location)
    EditText locationEditText;

    public ExploreFragment() {
        // Required empty public constructor
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
    }

    @OnClick(R.id.go)
    void startLocationSummaryActivity() {
        String location = locationEditText.getText().toString().trim();
        TravelerIoFacadeImpl.TravelerSettings.getInstance(getActivity()).setLocation(location);
        Intent intent = new Intent(getActivity(), LocationSummaryActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(R.id.location)
    void onTextChanged(CharSequence charSequence) {
        goButton.setEnabled(charSequence.length() > 0);
    }
}
