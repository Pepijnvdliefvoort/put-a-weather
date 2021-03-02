package com.funiculifunicula.putaweather.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtility {
    /**
     * Requests the current GPS location of the user. Requires the permissions {@code Manifest.permission.ACCESS_FINE_LOCATION} and {@code Manifest.permission.ACCESS_COARSE_LOCATION}. If these permissions are not granted, the user will be asked to grant these permissions.
     *
     * @param activity The activity the app is running on to check for permissions
     * @return The latitude and longitude in a {@link LatLng} object. Returns {@code null} if the required permissions were denied by the user after being prompted to grant access to the permissions.
     */
    public static LatLng getCurrentLocation(Activity activity) {
        if (
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return null;
        }

        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}