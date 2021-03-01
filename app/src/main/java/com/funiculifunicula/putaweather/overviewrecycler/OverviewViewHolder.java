package com.funiculifunicula.putaweather.overviewrecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.funiculifunicula.putaweather.R;

public class OverviewViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public OverviewViewHolder(@NonNull View itemView) {
        super(itemView);

        this.view = itemView;
    }

    public TextView getLocationNameView() {
        return view.findViewById(R.id.overview_item_locationName);
    }

    public ImageView getWeatherStateIconView() {
        return view.findViewById(R.id.overview_item_weatherStateIcon);
    }

    public TextView getTemperatureView() {
        return view.findViewById(R.id.overview_item_temperature);
    }
}
