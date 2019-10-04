package com.mandy.tencent.thanku;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mandy.tencent.R;
import com.mandy.tencent.baseactivity.BaseActivity;
import com.mandy.tencent.util.SharedToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThanksActivity extends BaseActivity {

    @BindView(R.id.imageFacebook)
    ImageView imageFacebook;
    @BindView(R.id.imageTwitter)
    ImageView imageTwitter;
    @BindView(R.id.imageLinkdin)
    ImageView imageLinkdin;
    @BindView(R.id.imageInstagram)
    ImageView imageInstagram;
    @BindView(R.id.tooolbar)
    Toolbar tooolbar;
    ActionBarDrawerToggle mToggle;

    SharedToken sharedToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_thanks, null, false);
        drawer.addView(contentView, 0);


        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);

    }

    @OnClick({R.id.imageFacebook, R.id.imageTwitter, R.id.imageLinkdin, R.id.imageInstagram})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageFacebook:
                break;
            case R.id.imageTwitter:
                break;
            case R.id.imageLinkdin:
                break;
            case R.id.imageInstagram:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tghomeinterior/"));
                startActivity(browserIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
