package com.mandy.tencent.termsandcondition;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.tencent.MainActivity;
import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.thanku.ThanksActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class TermsAndConditionActivity extends AppCompatActivity implements Controller.Terms {

    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.btnAgree)
    Button btnAgree;
    @BindView(R.id.txtview)
    TextView txtview;
    Controller controller;
    Dialog dialog;
    SharedToken sharedToken;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        ButterKnife.bind(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        userId = getIntent().getStringExtra("id");

        txtview.setMovementMethod(new ScrollingMovementMethod());

    }

    @OnClick(R.id.btnAgree)
    public void onViewClicked() {
        if (radioButton.isChecked()) {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setTerms(userId, "1");
            } else {
                Toast.makeText(this, "" + getResources().getString(R.string.noInterNet), Toast.LENGTH_SHORT).show();
            }
        } else {
            radioButton.requestFocus();
            Toast.makeText(this, "Accept the terms & conditions", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucess(Response<TermsAndConditionApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                sharedToken.setSharedata(response.body().getData().getToken().toString(), response.body().getData().getUserId().toString());
                Intent intent = new Intent(TermsAndConditionActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }
}
