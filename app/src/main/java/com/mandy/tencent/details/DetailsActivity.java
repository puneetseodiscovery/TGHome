package com.mandy.tencent.details;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mandy.tencent.R;
import com.mandy.tencent.projects.ProjectActivity;
import com.mandy.tencent.projects.ProjectApis;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.txtToolbar)
    TextView txtToolbar;
    @BindView(R.id.tooolbar)
    Toolbar tooolbar;
    @BindView(R.id.videoView)
    ViewPager viewPager;
    @BindView(R.id.circleindecator)
    CircleIndicator circleindecator;
    @BindView(R.id.txtHeading)
    TextView txtHeading;
    @BindView(R.id.txtRoom)
    TextView txtRoom;
    @BindView(R.id.txtM2)
    TextView txtM2;
    @BindView(R.id.txtT1)
    TextView txtT1;
    @BindView(R.id.txtT2)
    TextView txtT2;
    @BindView(R.id.txtComment)
    TextView txtComment;

    int pos;
    ArrayList<ProjectApis.EnquiryVideo> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        pos = Integer.parseInt(getIntent().getStringExtra("pos"));

        setSupportActionBar(tooolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");

        txtComment.setText(ProjectActivity.data.get(pos).getComment().toString());
        txtM2.setText("M2: " + ProjectActivity.data.get(pos).getM2().toString());
        txtHeading.setText(ProjectActivity.data.get(pos).getDropdown().toString());
        txtRoom.setText("Rooms: " + ProjectActivity.data.get(pos).getRoom().toString());
        txtT1.setText("T1: " + ProjectActivity.data.get(pos).getT1().toString());
        txtT2.setText("T2: " + ProjectActivity.data.get(pos).getT2().toString());

        for (int i = 0; i < ProjectActivity.data.get(pos).getEnquiryVideo().size(); i++) {
            arrayList.add(ProjectActivity.data.get(pos).getEnquiryVideo().get(i));
            setData(arrayList);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setData(ArrayList<ProjectApis.EnquiryVideo> arrayList) {
        PagerAdapter pagerAdapter = new ViewPagerAdapter(this, arrayList);
        viewPager.setAdapter(pagerAdapter);
        circleindecator.setViewPager(viewPager);

    }
}
