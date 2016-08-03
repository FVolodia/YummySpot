package com.yummyspots.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by FVolodia on 20.09.15.
 */
public class Alert {
    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void snackBar(ViewGroup viewGroup, String message){
        Snackbar snackbar = Snackbar
                .make(viewGroup, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
