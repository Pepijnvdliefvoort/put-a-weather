package com.funiculifunicula.putaweather.rest.openweathermap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.dialogs.ErrorDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class WeatherService {
    private final AppCompatActivity activity;
    private final String key;

    public WeatherService(AppCompatActivity activity) {
        this.activity = activity;
        key = activity.getString(R.string.openweathermap_key);
    }

    /**
     * Executes a single Volley request using a built URL to the OpenWeatherMap API
     *
     * @param requestType The type of request to make, used to identify the route used on the OpenWeatherMap API
     * @param onSuccess   The consumer executed upon successfully executing the request
     * @param onError     The consumer executed upon an error occurs
     * @param parameters  The parameters to append to the request made
     */
    private void makeRequest(WeatherRequestType requestType, Consumer<String> onSuccess, Consumer<VolleyError> onError, WeatherParameter... parameters) {
        // Base URL
        StringBuilder requestUrl = new StringBuilder("https://api.openweathermap.org/data/2.5/");

        // Parse type
        requestUrl.append(requestType.name().toLowerCase());

        // Base parameters
        requestUrl.append("?appid=").append(key).append("&units=metric").append("&lang=nl");

        // Parse parameters
        if (parameters != null) {
            for (WeatherParameter weatherParameter : parameters) {
                requestUrl.append("&").append(weatherParameter.getName()).append("=").append(weatherParameter.getValue());
            }
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                requestUrl.toString(),
                onSuccess::accept,
                error -> {
                    ErrorDialog errorDialog = new ErrorDialog(R.string.error_upon_retrieving_weather);
                    errorDialog.show(activity.getSupportFragmentManager());
                    onError.accept(error);
                });

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    /**
     * Calls a request to locate cities in a surrounding circle by the given latitude and longitude
     *
     * @param latitude   Geographical coordinate of the latitude of the center of the circle
     * @param longitude  Geographical coordinate of the longitude of the center of the circle
     * @param cities     Number of cities around the point that should be returned. The maximum is 50.
     * @param onResponse The consumer executed upon receiving the JSON data
     */
    public void requestCitiesInCircle(String latitude, String longitude, int cities, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError) {
        if (cities > 50) cities = 50;
        if (cities < 1) cities = 1;

        WeatherParameter[] parameters = new WeatherParameter[]{
                new WeatherParameter("lat", latitude),
                new WeatherParameter("lon", longitude),
                new WeatherParameter("cnt", Integer.toString(cities))
        };

        makeRequest(WeatherRequestType.FIND, (response) -> {
            try {
                JSONObject json = new JSONObject(response);
                onResponse.accept(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, onError, parameters);
    }

    /**
     * Calls a request to locate a specific city by its id
     *
     * @param id         The ID of the city to locate
     * @param onResponse The consumer executed upon receiving the JSON data
     */
    public void requestCityById(int id, Consumer<JSONObject> onResponse, Consumer<VolleyError> onError) {
        WeatherParameter[] parameters = new WeatherParameter[]{
                new WeatherParameter("id", Integer.toString(id))
        };
        makeRequest(WeatherRequestType.WEATHER, (response) -> {
            try {
                JSONObject json = new JSONObject(response);
                onResponse.accept(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, onError, parameters);
    }

    /**
     * Creates a {@link Bitmap} from an image, indicating the weather state, by making an async request to the OpenWeatherMap API
     *
     * @param icon      The name of the weather state icon to retrieve
     * @param onSuccess The consumer to be performed upon completion of retrieving the icon
     */
    public void getWeatherIcon(String icon, Consumer<Bitmap> onSuccess) {
        String url = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

        ImageRequest request = new ImageRequest(
                url,
                onSuccess::accept,
                100,
                100,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                error -> {
                    try {
                        throw new Exception(error.getCause());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }
}
