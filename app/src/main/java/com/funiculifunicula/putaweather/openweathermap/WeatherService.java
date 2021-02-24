package com.funiculifunicula.putaweather.openweathermap;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.funiculifunicula.putaweather.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class WeatherService {
    private final Context context;
    private final String key;

    public WeatherService(Context context) {
        this.context = context;
        key = context.getString(R.string.openweathermap_key);
    }

    /**
     * Executes a single Volley request using a built URL to the OpenWeatherMap API
     * @param requestType The type of request to make, used to identify the route used on the OpenWeatherMap API
     * @param onSuccess The consumer executed upon successfully executing the request
     * @param parameters The parameters to append to the request made
     */
    private void makeRequest(WeatherRequestType requestType, Consumer<String> onSuccess, WeatherParameter... parameters) {
        // Base URL
        StringBuilder requestUrl = new StringBuilder("https://api.openweathermap.org/data/2.5/");

        // Parse type
        requestUrl.append(requestType.name().toLowerCase());

        // Key
        requestUrl.append("?appid=").append(key);

        // Parse parameters
        if(parameters != null) {
            for(WeatherParameter weatherParameter : parameters) {
                requestUrl.append("&").append(weatherParameter.getName()).append("=").append(weatherParameter.getValue());
            }
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                requestUrl.toString(),
                onSuccess::accept,
                error -> Toast.makeText(context, "Fout bij het ophalen van de weersverwachting", Toast.LENGTH_LONG).show());

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Calls a request to locate cities in a surrounding circle by the given latitude and longitude
     * @param latitude Geographical coordinate of the latitude of the center of the circle
     * @param longitude Geographical coordinate of the longitude of the center of the circle
     * @param cities Number of cities around the point that should be returned. The maximum is 50.
     * @param onResponse The consumer executed upon receiving the JSON data
     */
    public void requestCitiesInCircle(String latitude, String longitude, int cities, Consumer<JSONObject> onResponse) {
        if(cities > 50) cities = 50;
        if(cities < 1) cities = 1;

        WeatherService service = new WeatherService(context);
        WeatherParameter[] parameters = new WeatherParameter[] {
                new WeatherParameter("lat", latitude),
                new WeatherParameter("lon", longitude),
                new WeatherParameter("cnt", Integer.toString(cities))
        };
        service.makeRequest(WeatherRequestType.FIND, (response) -> {
            try {
                JSONObject json = new JSONObject(response);
                onResponse.accept(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, parameters);
    }
}
