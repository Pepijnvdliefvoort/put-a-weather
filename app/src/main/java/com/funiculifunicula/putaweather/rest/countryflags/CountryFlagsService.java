package com.funiculifunicula.putaweather.rest.countryflags;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.function.Consumer;

public class CountryFlagsService {
    private final Context context;

    public CountryFlagsService(Context context) {
        this.context = context;
    }

    /**
     * Creates a {@link Bitmap} by making an async request to the Country Flags API
     *
     * @param countryCode The string value of the country to retrieve
     * @param onSuccess   The consumer to be performed upon completion of retrieving the country flag image
     */
    public void getCountryFlag(String countryCode, Consumer<Bitmap> onSuccess) {
        String url = "https://www.countryflags.io/" + countryCode.toLowerCase() + "/flat/64.png";

        ImageRequest request = new ImageRequest(
                url,
                onSuccess::accept,
                64,
                64,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                error -> {
                    try {
                        throw new Exception(error.getCause());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}