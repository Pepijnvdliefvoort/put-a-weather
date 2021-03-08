package com.funiculifunicula.putaweather;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.funiculifunicula.putaweather.fragments.SettingsFragment;
import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

        String googleMapsPackageName = "com.google.android.apps.maps";
        if(getPackageManager().getInstalledApplications(0).stream()
                .noneMatch(applicationInfo -> applicationInfo.packageName.equals(googleMapsPackageName))) {
            viewOnMapButton.setVisibility(View.INVISIBLE);
            return;
        }

        viewOnMapButton.setOnClickListener(v -> {
            if(latLng == null) {
                return;
            }

            Uri googleMapsUri = Uri.parse(String.format("google.streetview:cbll=%s,%s", latLng.longitude, latLng.latitude));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
            mapIntent.setPackage(googleMapsPackageName);
            startActivity(mapIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadImage();
    }

    private void reloadImage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useBackground = sharedPreferences.getBoolean(SettingsFragment.BACKGROUND_KEY, false);
        ImageView imageView = findViewById(R.id.detailImageViewLayout);

        if (!useBackground) {
            imageView.setImageResource(0);
            return;
        }

        String imagePathUri = sharedPreferences.getString(SettingsFragment.IMAGE_PATH_KEY, null);

        if (imagePathUri == null) {
            return;
        }

        if (imageView == null) {
            return;
        }

        imageView.setImageURI(Uri.parse(imagePathUri));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}