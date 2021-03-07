package com.funiculifunicula.putaweather.overviewrecycler;

import androidx.recyclerview.widget.RecyclerView;

public class OverviewItem {
    private final RecyclerView.Adapter<OverviewViewHolder> adapter;

    private final int cityId;
    private final String locationName;
    private final double temperature;
    private final String weatherStateIcon;
    private final String countryIcon;

    public OverviewItem(RecyclerView.Adapter<OverviewViewHolder> adapter, int cityId, String locationName, double temperature, String weatherStateIcon, String countryIcon) {
        this.adapter = adapter;

        this.cityId = cityId;
        this.locationName = locationName;
        this.temperature = temperature;
        this.weatherStateIcon = weatherStateIcon;
        this.countryIcon = countryIcon;
    }

    public int getCityId() {
        return cityId;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherStateIcon() {
        return weatherStateIcon;
    }

    public String getCountryIcon() {
        return countryIcon;
    }

    @Override
    public String toString() {
        return "OverviewItem{" +
                "adapter=" + adapter +
                ", cityId=" + cityId +
                ", locationName='" + locationName + '\'' +
                ", temperature=" + temperature +
                ", weatherStateIcon='" + weatherStateIcon + '\'' +
                ", countryIcon='" + countryIcon + '\'' +
                '}';
    }
}