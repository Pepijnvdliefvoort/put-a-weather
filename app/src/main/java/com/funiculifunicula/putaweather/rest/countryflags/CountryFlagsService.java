package com.funiculifunicula.putaweather.rest.countryflags;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    public void getCountryFlag(String countryCode, Consumer<Drawable> onSuccess) {
        String url = String.format("https://www.countryflags.io/%s/flat/64.png", countryCode.toLowerCase());

        ImageRequest request = new ImageRequest(
                url,
                response -> onSuccess.accept(new BitmapDrawable(context.getResources(), response)),
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