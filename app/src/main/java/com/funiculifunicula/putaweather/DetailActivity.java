package com.funiculifunicula.putaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindWeatherInformation();
        bindButtonListeners();
    }

    private void bindWeatherInformation() {
        WeatherService weatherService = new WeatherService(this);

        weatherService.requestCityById(getIntent().getExtras().getInt("cityId"), json -> {
            try {
                JSONObject coordJson = json.getJSONObject("coord");
                double lon = coordJson.getDouble("lon");
                double lat = coordJson.getDouble("lat");
                latLng = new LatLng(lon, lat);

                weatherService.getWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getString("icon"), image -> {
                    ((ImageView) findViewById(R.id.detailWeatherStateIcon)).setImageBitmap(image);
                });
                TextView cityName = findViewById(R.id.detailCityName);
                cityName.setText(json.getString("name") + ", ");

                TextView countryCode = findViewById(R.id.detailCountryCode);
                countryCode.setText(json.getJSONObject("sys").getString("country"));

                TextView temperature = findViewById(R.id.detailWeatherTemperatureValue);
                temperature.setText(json.getJSONObject("main").getString("temp") + "°C");

                TextView feelsLikeTemperature = findViewById(R.id.detailWeatherFeelsLikeTemperatureValue);
                feelsLikeTemperature.setText(json.getJSONObject("main").getString("feels_like") + "°C");

                TextView minimumTemperature = findViewById(R.id.detailWeatherMinimumValue);
                minimumTemperature.setText(json.getJSONObject("main").getString("temp_min") + "°C");

                TextView maximumTemperature = findViewById(R.id.detailWeatherMaximumValue);
                maximumTemperature.setText(json.getJSONObject("main").getString("temp_max") + "°C");

                TextView visibility = findViewById(R.id.detailWeatherVisibilityValue);
                visibility.setText((Integer.parseInt(json.getString("visibility")) / 1000) + "km");

                TextView clouds = findViewById(R.id.detailWeatherCloudsValue);
                clouds.setText(json.getJSONObject("clouds").getString("all") + "% " + getResources().getString(R.string.clouds_label));

                String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
                TextView cloudsDescription = findViewById(R.id.detailWeatherCloudsDescriptionValue);
                cloudsDescription.setText(weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    private void bindButtonListeners() {
        Button viewOnMapButton = findViewById(R.id.view_on_map_button);
        viewOnMapButton.setOnClickListener(v -> {
            if(latLng == null) {
                return;
            }

            Uri googleMapsUri = Uri.parse(String.format("google.streetview:cbll=%s,%s", latLng.longitude, latLng.latitude));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }
}