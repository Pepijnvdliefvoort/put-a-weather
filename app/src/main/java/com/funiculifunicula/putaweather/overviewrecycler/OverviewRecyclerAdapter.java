package com.funiculifunicula.putaweather.overviewrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.rest.countryflags.CountryFlagsService;
import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;

import java.util.ArrayList;
import java.util.List;

public class OverviewRecyclerAdapter extends RecyclerView.Adapter<OverviewViewHolder> {
    private final AppCompatActivity activity;
    private final List<OverviewItem> list;

    public OverviewRecyclerAdapter(AppCompatActivity activity) {
        this.activity = activity;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public OverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_item, parent, false);
        return new OverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewViewHolder holder, int position) {
        OverviewItem item = list.get(position);

        holder.getLocationNameView().setText(item.getLocationName());
        holder.getTemperatureView().setText(item.getTemperature() + " °C");

        WeatherService weatherService = new WeatherService(activity);
        weatherService.getWeatherIcon(item.getWeatherStateIcon(), image -> {
            holder.getWeatherStateIconView().setImageBitmap(image);
        });

        CountryFlagsService countryFlagsService = new CountryFlagsService(activity);
        countryFlagsService.getCountryFlag(item.getCountryIcon(), image -> {
            holder.getCountryIcon().setImageBitmap(image);
        });

        holder.attachClickListener(activity, item.getCityId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(OverviewItem item) {
        list.add(item);
    }

    public void clear() {
        list.clear();
    }
}
