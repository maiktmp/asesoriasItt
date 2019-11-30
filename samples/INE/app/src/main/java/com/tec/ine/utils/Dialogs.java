package com.tec.ine.utils;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.StringRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dialogs {
    public static void alert(Context context,
                             String title,
                             String message
    ) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }

    public static void alert(Context context,
                             String title,
                             String message,
                             CBDone cb
    ) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> cb.done())
                .show();
    }

    public static void alert(Context context,
                             String message
    ) {
        new MaterialAlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
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

    public static ProgressDialog progress(Context mContext,
                                          @StringRes int message) {
        ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setTitle(null);
        pDialog.setMessage(mContext.getString(message));
        pDialog.setCancelable(false);
        pDialog.show();

        return pDialog;
    }


}
