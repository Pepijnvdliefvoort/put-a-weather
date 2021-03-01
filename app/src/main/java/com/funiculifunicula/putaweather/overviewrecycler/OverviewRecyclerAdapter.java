package com.funiculifunicula.putaweather.overviewrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.funiculifunicula.putaweather.R;

import java.util.ArrayList;
import java.util.List;

public class OverviewRecyclerAdapter extends RecyclerView.Adapter<OverviewViewHolder> {
    private final List<OverviewItem> list;

    public OverviewRecyclerAdapter() {
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
        holder.getWeatherStateIconView().setImageDrawable(item.getWeatherStateIcon());
        holder.getTemperatureView().setText(item.getTemperature() + " °C");
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