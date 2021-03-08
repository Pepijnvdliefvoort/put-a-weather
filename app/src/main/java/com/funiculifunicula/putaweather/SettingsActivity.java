package com.funiculifunicula.putaweather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.funiculifunicula.putaweather.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsFragment = new SettingsFragment();

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingsFragment.reloadBackgroundIcon();
    }
}