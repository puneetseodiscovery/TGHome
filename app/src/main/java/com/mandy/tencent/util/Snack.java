package com.mandy.tencent.util;


import android.app.Activity;
import android.support.design.widget.Snackbar;

public class Snack {
    public static Snackbar snackbar(Activity context, String string) {
        Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }
}
