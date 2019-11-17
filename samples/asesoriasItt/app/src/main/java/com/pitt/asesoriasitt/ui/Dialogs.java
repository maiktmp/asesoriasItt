package com.pitt.asesoriasitt.ui;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Dialogs {

    public static void alert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
