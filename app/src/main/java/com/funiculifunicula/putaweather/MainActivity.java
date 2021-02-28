package com.funiculifunicula.putaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.funiculifunicula.putaweather.dialogs.ErrorDialog;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
    }
}