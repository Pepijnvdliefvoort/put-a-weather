package com.funiculifunicula.putaweather.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.funiculifunicula.putaweather.R;

public class ErrorDialog extends DialogFragment {
    public static final String TAG = "error";

    private final int stringId;

    public ErrorDialog(int stringId) {
        this.stringId = stringId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(R.string.dialog_title_error)
                .setMessage(stringId)
                .setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
                    dialog.dismiss();
                });

        return builder.create();
    }

    public void show(@NonNull FragmentManager manager) {
        super.show(manager, TAG);
    }
}