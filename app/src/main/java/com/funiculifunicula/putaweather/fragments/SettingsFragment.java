package com.funiculifunicula.putaweather.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.funiculifunicula.putaweather.R;


public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int PICK_IMAGE = 1;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        Preference preference = findPreference("image_picker");
        preference.setOnPreferenceClickListener(pref -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Selecteer afbeelding"), PICK_IMAGE);
            return false;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }
}
