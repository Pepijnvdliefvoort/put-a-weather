package com.funiculifunicula.putaweather.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.funiculifunicula.putaweather.R;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int PICK_IMAGE = 1;
    public static final String IMAGE_PATH_KEY = "image_path";
    public static final String BACKGROUND_KEY = "use_background";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        Preference preference = findPreference("image_picker");

        preference.setOnPreferenceClickListener(pref -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, "Selecteer afbeelding"), PICK_IMAGE);

            return false;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Activity activity = getActivity();

            if (data == null) {
                return;
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            sharedPreferences
                    .edit()
                    .putString(IMAGE_PATH_KEY, data.getDataString())
                    .commit();
        }
    }

    public void reloadBackgroundIcon() {
        Preference preference = findPreference("image_picker");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String imageUri = sharedPreferences.getString(IMAGE_PATH_KEY, null);

        if (imageUri != null) {
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(Uri.parse(imageUri));
                Drawable drawable = Drawable.createFromStream(inputStream, "image_background");

                preference.setIcon(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
