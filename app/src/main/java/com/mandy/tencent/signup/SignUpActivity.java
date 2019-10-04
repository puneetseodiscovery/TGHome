package com.mandy.tencent.signup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.login.LoginActivity;
import com.mandy.tencent.termsandcondition.TermsAndConditionActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.EditTextValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements Controller.SignUp {

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtBankid)
    EditText edtBankid;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtRepassword)
    EditText edtRepassword;
    @BindView(R.id.btnSingup)
    Button btnSingup;
    @BindView(R.id.txtSignup)
    TextView txtSignup;

    Controller controller;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        dialog = new Dialog(this);
        controller = new Controller(this);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnSingup)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtBankid.getText().toString()) || TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString()) || TextUtils.isEmpty(edtRepassword.getText().toString())) {
            EditTextValidation.edit(edtBankid);
            EditTextValidation.edit(edtEmail);
            EditTextValidation.edit(edtName);
            EditTextValidation.edit(edtPassword);
            EditTextValidation.edit(edtRepassword);
        } else if (!edtPassword.getText().toString().equals(edtRepassword.getText().toString())) {
            edtRepassword.setError("Password not match");
            edtRepassword.requestFocus();
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setSignUp(edtName.getText().toString(), edtBankid.getText().toString(), edtPassword.getText().toString(), edtRepassword.getText().toString(), edtEmail.getText().toString());
            } else {
                Toast.makeText(this, "" + getResources().getString(R.string.noInterNet), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onSucess(Response<SignUpApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Intent intent = new Intent(SignUpActivity.this, TermsAndConditionActivity.class);
                intent.putExtra("id", response.body().getData().getUserId().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }
}
