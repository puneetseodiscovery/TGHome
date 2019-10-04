package com.mandy.tencent.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.tencent.MainActivity;
import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.signup.SignUpActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.EditTextValidation;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Controller.Login {

    @BindView(R.id.edtId)
    EditText edtId;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtLoginsub)
    TextView txtLoginsub;
    @BindView(R.id.txtSignup)
    TextView txtSignup;

    Dialog dialog;
    SharedToken sharedToken;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        controller = new Controller(this);

        txtSignup.setVisibility(View.GONE);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtId.getText().toString()) || edtId.getText().length() > 18) {
            edtId.setError(getResources().getString(R.string.PersonalId));
        } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError("Enter your password");
            edtPassword.requestFocus();
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setLogin(edtId.getText().toString(), edtPassword.getText().toString());
            } else {
                Toast.makeText(this, "" + getResources().getString(R.string.noInterNet), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onSucess(Response<LoginApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                sharedToken.setSharedata(response.body().getData().getToken(), response.body().getData().getUserId().toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }
}
