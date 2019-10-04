package com.mandy.tencent.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;


import com.mandy.tencent.R;

public class ProgressBarClass {

    public static Dialog showProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.custom_progress);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }

}
