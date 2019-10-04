package com.mandy.tencent.myaccount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.R;
import com.mandy.tencent.baseactivity.BaseActivity;
import com.mandy.tencent.baseactivity.GetStatusApis;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.login.LoginActivity;
import com.mandy.tencent.message.MessageActivity;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.setting.SettingActivity;
import com.mandy.tencent.upgrade.UpgradeActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.Config;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity implements Controller.GetProfile, Controller.GetStatus {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.txtToolbar)
    TextView textView;
    @BindView(R.id.imageProfile)
    RoundedImageView imageProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.cardProject)
    CardView cardProject;
    @BindView(R.id.cardShop)
    CardView cardShop;
    @BindView(R.id.cardSetting)
    CardView cardSetting;
    @BindView(R.id.cardHelp)
    CardView cardHelp;
    @BindView(R.id.cardLogout)
    CardView cardLogout;
    @BindView(R.id.txtEmail)
    TextView txtEmail;

    SharedToken sharedToken;
    Controller controller;
    Dialog dialog;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);
        controller = new Controller((Controller.GetProfile) this, (Controller.GetStatus) this);
        dialog = ProgressBarClass.showProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("My Account");


        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetProfile(sharedToken.getUserId());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }


    }

    @OnClick({R.id.cardProject, R.id.cardShop, R.id.cardSetting, R.id.cardHelp, R.id.cardLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardProject:

                break;
            case R.id.cardShop:
                Intent intentShop = new Intent(this, UpgradeActivity.class);
                startActivity(intentShop);
                break;
            case R.id.cardSetting:
                Intent intentSetting = new Intent(this, SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.cardHelp:
                if (type.equals("normal")) {
                    showDialod2();
                } else {
                    Intent message = new Intent(MyAccountActivity.this, MessageActivity.class);
                    startActivity(message);
                }
                break;
            case R.id.cardLogout:
                showDialod();
                break;
        }
    }

    @Override
    public void onSucess(Response<GetProfileApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                GetProfileApi.Data data = response.body().getData();
                txtName.setText(data.getName());
                txtEmail.setText(data.getEmail());
                if (data.getProfileImage() != null) {
                    Glide.with(this).load(Config.GET_PROFILE_IMAGE + data.getProfileImage()).dontAnimate().dontTransform().into(imageProfile);
                }

            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucessStatus(Response<GetStatusApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                type = response.body().getData().getSubscriptionStatus().toString();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // for check the user type
    private void showDialod2() {
        DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent1 = new Intent(MyAccountActivity.this, UpgradeActivity.class);
                        startActivity(intent1);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Want to upgrade your package ?").setPositiveButton("Yes", dialogClick).setNegativeButton("No", dialogClick).show();


    }


    // for logout
    private void showDialod() {
        DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        sharedToken.clearShaerd();
                        Intent intent2 = new Intent(MyAccountActivity.this, LoginActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        finish();
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to logout?").setPositiveButton("Yes", dialogClick).setNegativeButton("No", dialogClick).show();
        builder.setCancelable(false);

    }

}
