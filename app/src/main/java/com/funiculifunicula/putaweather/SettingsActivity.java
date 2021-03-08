package com.funiculifunicula.putaweather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.funiculifunicula.putaweather.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
