package com.funiculifunicula.putaweather;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        WeatherService weatherService = new WeatherService(this);

        weatherService.requestCityById(getIntent().getExtras().getInt("cityId"), json -> {
            try {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}