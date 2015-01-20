package com.traveler.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.traveler.R;
import com.traveler.fragment.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
//    @InjectView(R.id.go)
//    Button goButton;
//
//    @InjectView(R.id.location)
//    EditText locationEditText;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.inject(this);
        // TODO disabled in testing
        //goButton.setEnabled(false);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
//
//    @OnClick(R.id.go)
//    void startLocationSummaryActivity() {
//        String location = locationEditText.getText().toString().trim();
//        TravelerIoFacadeImpl.TravelerSettings.getInstance(MainActivity.this).setLocation(location);
//
//        Intent intent = new Intent(MainActivity.this, LocationSummaryActivity.class);
//        startActivity(intent);
//    }
//
//    @OnTextChanged(R.id.location)
//    void onTextChanged(CharSequence charSequence) {
//        goButton.setEnabled(charSequence.length() > 0);
//    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
