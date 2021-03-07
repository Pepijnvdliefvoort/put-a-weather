package com.funiculifunicula.putaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funiculifunicula.putaweather.rest.openweathermap.WeatherService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.e("test", String.valueOf(getIntent().getExtras().getInt("cityId")));

        WeatherService weatherService = new WeatherService(this);

        weatherService.requestCityById(getIntent().getExtras().getInt("cityId"), json -> {
            try {
                weatherService.getWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getString("icon"), image -> {
                    ((ImageView) findViewById(R.id.detailWeatherStateIcon)).setImageBitmap(image);
                });
                ((TextView) findViewById(R.id.detailCityName)).setText(json.getString("name") + ", ");
                ((TextView) findViewById(R.id.detailCountryCode)).setText(json.getJSONObject("sys").getString("country"));
                ((TextView) findViewById(R.id.detailWeatherTemperatureValue)).setText(json.getJSONObject("main").getString("temp") +  "°C");
                ((TextView) findViewById(R.id.detailWeatherFeelsLikeTemperatureValue)).setText(json.getJSONObject("main").getString("feels_like") + "°C");
                ((TextView) findViewById(R.id.detailWeatherMinimumValue)).setText(json.getJSONObject("main").getString("temp_min") + "°C");
                ((TextView) findViewById(R.id.detailWeatherMaximumValue)).setText(json.getJSONObject("main").getString("temp_max") + "°C");
                ((TextView) findViewById(R.id.detailWeatherVisibilityValue)).setText((Integer.parseInt(json.getString("visibility")) / 1000) + "km");
                ((TextView) findViewById(R.id.detailWeatherCloudsValue)).setText(json.getJSONObject("clouds").getString("all") + "% bewolkt");
                String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
                ((TextView) findViewById(R.id.detailWeatherCloudsDescriptionValue)).setText(weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, null);
    }
}