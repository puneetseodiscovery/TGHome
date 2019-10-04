package com.mandy.tencent.util;

import android.text.TextUtils;
import android.widget.EditText;

public class EditTextValidation {

    public static void edit(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("Invalid data");
            editText.requestFocus();
        }
    }
}
