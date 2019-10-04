package com.mandy.tencent.nointernet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mandy.tencent.MainActivity;
import com.mandy.tencent.R;
import com.mandy.tencent.login.LoginActivity;
import com.mandy.tencent.util.CheckInternet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoInternetActivity extends AppCompatActivity {

    @BindView(R.id.btnRetry)
    Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRetry)
    public void onViewClicked() {
        if (CheckInternet.isInternetAvailable(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No Internet connection", Snackbar.LENGTH_LONG).show();

        }
    }
}
