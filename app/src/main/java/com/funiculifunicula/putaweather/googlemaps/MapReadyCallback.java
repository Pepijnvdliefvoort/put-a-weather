package com.funiculifunicula.putaweather.googlemaps;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.funiculifunicula.putaweather.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

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

        map.setOnMapClickListener(latLng -> activity.getOverviewFragment().updateRecyclerView(latLng));
    }
}