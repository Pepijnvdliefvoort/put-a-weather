package com.funiculifunicula.putaweather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.rest.countryflags.CountryFlagsService;
import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;
import com.funiculifunicula.putaweather.overviewrecycler.OverviewItem;
import com.funiculifunicula.putaweather.overviewrecycler.OverviewRecyclerAdapter;
import com.funiculifunicula.putaweather.utility.LocationUtility;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OverviewFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;
    private OverviewRecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview_fragment, container, false);

        initializeRecyclerView();

        return view;
    }

    private void initializeRecyclerView() {
        recyclerAdapter = new OverviewRecyclerAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        updateRecyclerView(null);
    }

    public void updateRecyclerView(LatLng latLng) {
        recyclerView.smoothScrollToPosition(0);

        ProgressBar loader = view.findViewById(R.id.overview_loader);
        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        int oldContentSize = recyclerAdapter.getItemCount();
        recyclerAdapter.clear();
        recyclerAdapter.notifyItemRangeRemoved(0, oldContentSize);

        if (latLng == null) {
            if((latLng = LocationUtility.getCurrentLocation(getActivity())) == null) {
                return;
            }
        }

        WeatherService weatherService = new WeatherService(getActivity());

        weatherService.requestCitiesInCircle(Double.toString(latLng.latitude), Double.toString(latLng.longitude), 50, json -> {
            try {
                JSONArray citiesJson = json.getJSONArray("list");

                for(int i = 0; i < citiesJson.length(); i++) {
                    JSONObject city = citiesJson.getJSONObject(i);

                    String locationName = city.getString("name");
                    double temperature = city.getJSONObject("main").getDouble("temp");
                    String iconName = city.getJSONArray("weather").getJSONObject(0).getString("icon");
                    String countryCode = city.getJSONObject("sys").getString("country");

                    OverviewItem overviewItem = new OverviewItem(recyclerAdapter, locationName, temperature, iconName, countryCode);
                    recyclerAdapter.add(overviewItem);
                    recyclerAdapter.notifyItemInserted(recyclerAdapter.getItemCount() - 1);
                }

                loader.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}