package com.pitt.asesoriasitt.ui;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pitt.asesoriasitt.utils.callbacks.CBDone;

public class Dialogs {

    public static void alert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void alert(Context context,
                             String message,
                             CBDone cb
    ) {
        new MaterialAlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton("Aceptar", (dialogInterface, i) -> cb.done())
                .show();
    }
}
