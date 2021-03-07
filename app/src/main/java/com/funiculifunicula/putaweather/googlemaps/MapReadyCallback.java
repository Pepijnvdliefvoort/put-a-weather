package com.funiculifunicula.putaweather.googlemaps;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.funiculifunicula.putaweather.MainActivity;
import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.dialogs.ErrorDialog;
import com.funiculifunicula.putaweather.exceptions.LastKnownLocationNotFoundException;
import com.funiculifunicula.putaweather.utility.LocationUtility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MapReadyCallback implements OnMapReadyCallback {
    private final MainActivity activity;

    public MapReadyCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
            return;
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        try {
            LatLng currentLatLng = LocationUtility.getCurrentLocation(activity);
            if(currentLatLng != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 8));
            }
        }
        catch(LastKnownLocationNotFoundException e) {
            ErrorDialog errorDialog = new ErrorDialog(R.string.error_upon_retrieving_location);
            errorDialog.show(activity.getSupportFragmentManager());
        }

        map.setOnMapClickListener(latLng -> activity.getOverviewFragment().updateRecyclerView(latLng));
    }
}
