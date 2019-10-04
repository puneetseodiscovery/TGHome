package com.mandy.tencent.upgrade;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mandy.tencent.R;
import com.mandy.tencent.baseactivity.GetStatusApis;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.thanku.ThanksActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class UpgradeActivity extends AppCompatActivity implements Controller.UpdateStatus {

    @BindView(R.id.radioStanderd)
    RadioButton radioStanderd;
    @BindView(R.id.radioAdvance)
    RadioButton radioAdvance;
    @BindView(R.id.radioNoThanks)
    RadioButton radioNoThanks;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tooolbar)
    Toolbar tooolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    Controller controller;
    Dialog dialog;
    SharedToken sharedToken;
    String type = "normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ButterKnife.bind(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);

        setSupportActionBar(tooolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");

        radioNoThanks.isChecked();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioStanderd:
                        type = "standerd";
                        break;
                    case R.id.radioAdvance:
                        type = "advance";
                        break;

                    case R.id.radioNoThanks:
                        type = "normal";
                        break;
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isInternetAvailable(UpgradeActivity.this)) {
//                    dialog.show();
//                    controller.setUpdateStatus(sharedToken.getUserId(), type);
                    startSwish(UpgradeActivity.this, "c28a4061470f4af48973bd2a4642b4fa", "merchant%253A%252F%252F",15);
                } else {
                    startActivity(new Intent(UpgradeActivity.this, NoInternetActivity.class));
                }
            }
        });


        isSwishAppInstalled(UpgradeActivity.this, "se.bankgirot.swish");
    }


    public static boolean startSwish(Activity activity, String token, String
            callBackUrl, int requestCode) {
        if ( token == null || token.length() == 0 || callBackUrl == null ||
                callBackUrl.length() == 0 || activity == null) {
            return false;
        }
        Uri scheme =
                Uri.parse("swish://paymentrequest?token="+token+"&callbackurl="+callBackUrl
                );
        Intent intent = new Intent(Intent.ACTION_VIEW, scheme);
        intent.setPackage("se.bankgirot.swish");
        boolean started=true;
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e){
            started=false;
        }
        return started;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<UpgradeApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Intent intent = new Intent(UpgradeActivity.this, ThanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15) {
            if (resultCode == RESULT_OK) {
                String abc = data.getData().toString();
                Log.d("++++++++++", "pay" + abc);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Transaction cancel", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }


    //Swish package name is “se.bankgirot.swish”
    protected boolean isSwishAppInstalled(Context _context, String
            SwishPackageName) {
        boolean isSwishInstalled = false;
        try {
            _context.getPackageManager().getApplicationInfo(SwishPackageName, 0);
            isSwishInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return isSwishInstalled;
    }
}