package com.traveler.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.traveler.Extra;
import com.traveler.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LocationSearchActivity extends Activity {
    @InjectView(R.id.go)
    Button goButton;

    @InjectView(R.id.location)
    EditText locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        ButterKnife.inject(this);
        goButton.setEnabled(false);
    }

    @OnClick(R.id.go)
    void startLocationSummaryActivity() {
        Intent intent = new Intent(LocationSearchActivity.this, LocationSummaryActivity.class);
        intent.putExtra(Extra.LOCATION, locationEditText.getText().toString());
        startActivity(intent);
    }

    @OnTextChanged(R.id.location)
    void onTextChanged(CharSequence charSequence) {
        goButton.setEnabled(charSequence.length() > 0);
    }
}
