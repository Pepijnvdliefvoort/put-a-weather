package com.funiculifunicula.putaweather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.funiculifunicula.putaweather.MainActivity;
import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.googlemaps.MapReadyCallback;
import com.google.android.gms.maps.MapView;

public class MapFragment extends Fragment {
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        initializeMapView(view, savedInstanceState);

        return view;
    }

    private void initializeMapView(View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new MapReadyCallback((MainActivity) getActivity()));

        mapView.onResume();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
}