package com.funiculifunicula.putaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.funiculifunicula.putaweather.dialogs.ErrorDialog;
import com.funiculifunicula.putaweather.fragments.MapFragment;
import com.funiculifunicula.putaweather.fragments.OverviewFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private OverviewFragment overviewFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overviewFragment = (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.overviewFragment);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean accessGranted = Arrays.stream(grantResults).anyMatch(r -> r == PackageManager.PERMISSION_GRANTED);

        switch(requestCode) {
            case 1:
                if(accessGranted) {
                    finish();
                    startActivity(getIntent());
                } else {
                    ErrorDialog errorDialog = new ErrorDialog(R.string.error_required_permission_not_granted);
                    errorDialog.show(getSupportFragmentManager(), ErrorDialog.TAG);
                }

                break;
            default:
                break;
        }
    }

    public OverviewFragment getOverviewFragment() {
        return overviewFragment;
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }
}