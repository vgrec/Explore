package com.traveler.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.traveler.R;

/**
 * @author vgrec, created on 3/5/15.
 */
public class ExploreMapFragment extends SupportMapFragment {
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGITUDE = "lng";
    public static final String KEY_ADDRESS = "address";

    private LatLng location;
    private String address;

    public ExploreMapFragment() {
    }

    public static ExploreMapFragment newInstance(Bundle args) {
        ExploreMapFragment mapFragment = new ExploreMapFragment();
        mapFragment.setArguments(args);
        return mapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                double latitude = Double.valueOf(getArguments().getString(KEY_LATITUDE));
                double longitude = Double.valueOf(getArguments().getString(KEY_LONGITUDE));
                location = new LatLng(latitude, longitude);
                address = getArguments().getString(KEY_ADDRESS);
            } catch (NumberFormatException e) {
                Log.d("Explore", "Could not parse latitude and longitude");
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isGooglePlayServicesAvailable(getFragmentManager())) {
            showPlaceLocationOnMap();
        }
    }

    private void showPlaceLocationOnMap() {
        if (location == null) {
            return;
        }

        Marker marker = getMap().addMarker(new MarkerOptions()
                .position(location)
                .title(address));
        marker.showInfoWindow();
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
    }

    /**
     * Checks whether the device has Google Play Services installed.
     * If not, an error dialog is displayed and user is suggested to download it.
     */
    public boolean isGooglePlayServicesAvailable(FragmentManager manager) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (status == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0);
            if (errorDialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(manager, null);
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.maps_not_available), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /**
     * Define a DialogFragment that displays an error dialog
     * in case Google Play Services are not available
     */
    public static class ErrorDialogFragment extends DialogFragment {
        private Dialog dialog;

        public ErrorDialogFragment() {
            dialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return dialog;
        }
    }
}
