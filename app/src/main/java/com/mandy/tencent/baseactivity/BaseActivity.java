package com.mandy.tencent.baseactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.MainActivity;
import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.kotlin.Main2Activity;
import com.mandy.tencent.login.LoginActivity;
import com.mandy.tencent.myaccount.GetProfileApi;
import com.mandy.tencent.myaccount.MyAccountActivity;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.projects.ProjectActivity;
import com.mandy.tencent.upgrade.UpgradeActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.Config;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import retrofit2.Response;

public class BaseActivity extends AppCompatActivity implements Controller.GetProfile, Controller.GetStatus {

    public NavigationView navigation;
    public DrawerLayout drawer;

    Toolbar toolbar;

    RoundedImageView roundedImageView;
    TextView txtName;

    Dialog dialog;
    Controller controller;
    SharedToken sharedToken;
    String type;
    View hView;
    ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        controller = new Controller((Controller.GetStatus) this, (Controller.GetProfile) this);
        sharedToken = new SharedToken(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.tooolbar);


        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        drawer.addDrawerListener(mToggle);
        mToggle.syncState();

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        hView = navigation.inflateHeaderView(R.layout.header);
        roundedImageView = hView.findViewById(R.id.imageProfile);
        txtName = hView.findViewById(R.id.txtName);


        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetStatus(sharedToken.getUserId());
            controller.setGetProfile(sharedToken.getUserId());

        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

        onNavigationClick();


    }

    private void onNavigationClick() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intentHome = new Intent(BaseActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        drawer.closeDrawers();
                        break;
                    case R.id.shop:
                        Intent intent1 = new Intent(BaseActivity.this, UpgradeActivity.class);
                        startActivity(intent1);
                        drawer.closeDrawers();
                        break;

                    case R.id.myaccount:
                        Intent intentProfile = new Intent(BaseActivity.this, MyAccountActivity.class);
                        startActivity(intentProfile);
                        drawer.closeDrawers();
                        break;
                    case R.id.logout:
                        showDialod();
                        break;

                    case R.id.support:
                        if (type.equals("normal")) {
                            showDialod2();
                        } else {
                            Intent message = new Intent(BaseActivity.this, Main2Activity.class);
                            startActivity(message);
                            drawer.closeDrawers();
                        }
                        break;
                    case R.id.project:
                        Intent intent = new Intent(BaseActivity.this, ProjectActivity.class);
                        startActivity(intent);
                        drawer.closeDrawers();
                        break;

                }
                return false;
            }

        });
    }


    // for check the user type
    private void showDialod2() {
        DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent1 = new Intent(BaseActivity.this, UpgradeActivity.class);
                        startActivity(intent1);
                        drawer.closeDrawers();
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
                        Intent intent2 = new Intent(BaseActivity.this, LoginActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onSucessStatus(Response<GetStatusApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                type = response.body().getData().getSubscriptionStatus().toString();
            } else {
                Toast.makeText(BaseActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(BaseActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucess(Response<GetProfileApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                txtName.setText(response.body().getData().getName().toString());
                if (response.body().getData().getProfileImage() != null) {
                    Glide.with(BaseActivity.this).load(Config.GET_PROFILE_IMAGE + response.body().getData().getProfileImage())
                            .dontTransform().dontAnimate().into(roundedImageView);
                }
            } else {
                Toast.makeText(BaseActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(BaseActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }


}
