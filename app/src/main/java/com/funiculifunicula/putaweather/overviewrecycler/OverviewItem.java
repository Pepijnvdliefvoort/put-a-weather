package com.funiculifunicula.putaweather.overviewrecycler;

import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.RecyclerView;

public class OverviewItem {
    private final RecyclerView.Adapter<OverviewViewHolder> adapter;

    private final String locationName;
    private final double temperature;
    private Drawable weatherStateIcon;
    private Drawable countryIcon;

    public OverviewItem(RecyclerView.Adapter<OverviewViewHolder> adapter, String locationName, double temperature) {
        this.adapter = adapter;

        this.locationName = locationName;
        this.temperature = temperature;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setWeatherStateIcon(Drawable drawable) {
        weatherStateIcon = drawable;
        adapter.notifyDataSetChanged();
    }

    public Drawable getWeatherStateIcon() {
        return weatherStateIcon;
    }

    public void setCountryIcon(Drawable drawable) {
        countryIcon = drawable;
        adapter.notifyDataSetChanged();
    }

    public Drawable getCountryIcon() {
        return countryIcon;
    }

    @Override
    public String toString() {
        return "OverviewItem{" +
                ", locationName='" + locationName + '\'' +
                ", temperature=" + temperature +
                ", weatherStateIcon=" + weatherStateIcon +
                ", countryIcon=" + countryIcon +
                '}';
    }
}