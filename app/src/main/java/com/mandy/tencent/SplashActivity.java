package com.mandy.tencent;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.mandy.tencent.login.LoginActivity;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progress_horizontal)
    ProgressBar progressHorizontal;
    Handler handler;
    int i = 0;
    SharedToken sharedToken;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);

        handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (i < 100) {

                    i++;

                    if (i == 99) {
                        if (TextUtils.isEmpty(sharedToken.getShared())) {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            progressHorizontal.setProgress(i);
                            progressHorizontal.setSecondaryProgress(i + 20);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(100); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
