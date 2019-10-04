package com.mandy.tencent.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.MessageApis;
import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.myaccount.GetProfileApi;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.Config;
import com.mandy.tencent.util.EditTextValidation;
import com.mandy.tencent.util.MultiPart;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements Controller.GetProfile, Controller.UploadImage, Controller.ChangePassword, Controller.UpdateAccount {

    public static int REQUEST_CAMERA = 122;
    public static int MY_PERMISSIONS_REQUEST_CAMERA = 555;
    public static int RESULT_LOAD_IMAGE = 111;

    @BindView(R.id.txtToolbar)
    TextView txtToolbar;
    @BindView(R.id.btnProfile)
    Button btnProfile;
    @BindView(R.id.btnPassword)
    Button btnPassword;
    @BindView(R.id.editOldPasword)
    EditText editOldPasword;
    @BindView(R.id.editNewPassword)
    EditText editNewPassword;
    @BindView(R.id.editConfirmPassword)
    EditText editConfirmPassword;
    @BindView(R.id.btnCreate)
    Button btnCreate;
    @BindView(R.id.linearPassword)
    LinearLayout linearPassword;
    @BindView(R.id.profileImage)
    RoundedImageView profileImage;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.linearProfile)
    LinearLayout linearProfile;
    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    Dialog dialog;
    SharedToken sharedToken;
    Controller controller;
    Bitmap bitmap;
    MultipartBody.Part part;

    String[] dialogOptions = new String[]{"Camera", "Gallery", "Cancel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        controller = new Controller((Controller.UploadImage) this, (Controller.GetProfile) this, (Controller.ChangePassword) this, (Controller.UpdateAccount) this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetProfile(sharedToken.getUserId());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        txtToolbar.setText("Setting");

        btnPassword.setBackgroundColor(getResources().getColor(R.color.white));
        btnPassword.setTextColor(getResources().getColor(R.color.black));

        btnProfile.setBackgroundColor(getResources().getColor(R.color.black));
        btnProfile.setTextColor(getResources().getColor(R.color.white));

        linearProfile.setVisibility(View.VISIBLE);
        linearPassword.setVisibility(View.GONE);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SettingActivity.this, Manifest.permission.CAMERA)) {

                        ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    } else {
                        ActivityCompat.requestPermissions(SettingActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }

                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
            }
        });

    }

    @OnClick({R.id.btnProfile, R.id.btnPassword, R.id.btnCreate, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnProfile:

                btnPassword.setBackgroundColor(getResources().getColor(R.color.white));
                btnPassword.setTextColor(getResources().getColor(R.color.black));

                btnProfile.setBackgroundColor(getResources().getColor(R.color.black));
                btnProfile.setTextColor(getResources().getColor(R.color.white));

                linearProfile.setVisibility(View.VISIBLE);
                linearPassword.setVisibility(View.GONE);


                break;
            case R.id.btnPassword:
                btnProfile.setBackgroundColor(getResources().getColor(R.color.white));
                btnProfile.setTextColor(getResources().getColor(R.color.black));

                btnPassword.setBackgroundColor(getResources().getColor(R.color.black));
                btnPassword.setTextColor(getResources().getColor(R.color.white));

                linearPassword.setVisibility(View.VISIBLE);
                linearProfile.setVisibility(View.GONE);

                break;

            case R.id.btnCreate:
                setPassword();
                break;
            case R.id.btnSave:
                if (TextUtils.isEmpty(editName.getText().toString())) {
                    editName.setError("Enter your name");
                } else if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    editEmail.setError("Enter your email");
                } else {
                    if (CheckInternet.isInternetAvailable(this)) {
                        dialog.show();
                        controller.setUpdateAccount(sharedToken.getUserId(), editName.getText().toString(), editEmail.getText().toString());
                    } else {
                        startActivity(new Intent(this, NoInternetActivity.class));
                    }
                }
                break;
        }
    }

    @Override
    public void onSucess(Response<GetProfileApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                GetProfileApi.Data data = response.body().getData();
                editName.setText(data.getName());
                editEmail.setText(data.getEmail());
                if (data.getProfileImage() != null) {
                    Glide.with(this).load(Config.GET_PROFILE_IMAGE + data.getProfileImage()).dontTransform().dontAnimate().into(profileImage);
                }

            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucessUpload(Response<UploadImageApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucessChange(Response<MessageApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                editOldPasword.setText("");
                editNewPassword.setText("");
                editConfirmPassword.setText("");
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSucessAccount(Response<UpdateAccountApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    private void setPassword() {
        if (TextUtils.isEmpty(editOldPasword.getText().toString()) || TextUtils.isEmpty(editNewPassword.getText().toString()) ||
                TextUtils.isEmpty(editConfirmPassword.getText().toString())) {
            EditTextValidation.edit(editOldPasword);
            EditTextValidation.edit(editNewPassword);
            EditTextValidation.edit(editConfirmPassword);
        } else if (!editNewPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
            editConfirmPassword.setError("Passowrd does not match");
            editConfirmPassword.requestFocus();
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setChangePassword(sharedToken.getUserId(), editOldPasword.getText().toString(), editNewPassword.getText().toString(), editConfirmPassword.getText().toString());
            } else {
                startActivity(new Intent(this, NoInternetActivity.class));
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            try {
                MultiPart multi = new MultiPart(this, "image");
                part = multi.sendImageFileToserver(bitmap);
                dialog.show();
                controller.setUploadImage(sharedToken.getUserId(), part);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                MultiPart multi = new MultiPart(this, "image");
                part = multi.sendImageFileToserver(bitmap);
                dialog.show();
                controller.setUploadImage(sharedToken.getUserId(), part);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    //    chose image from camera and gallery
    private void showDialg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Select Images");
        builder.setCancelable(false);
        //builder.setPositiveButton("OK", null);

        builder.setItems(dialogOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ("Camera".equals(dialogOptions[which])) {

                    if (ContextCompat.checkSelfPermission(SettingActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(SettingActivity.this, Manifest.permission.CAMERA)) {

                            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        } else {
                            ActivityCompat.requestPermissions(SettingActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }

                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }


                } else if ("Gallery".equals(dialogOptions[which])) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);

                } else if ("Cancel".equals(dialogOptions[which])) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
}
