package com.mandy.tencent.projects;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity implements Controller.Projects {

    @BindView(R.id.reycelerView)
    RecyclerView recyclerView;
    Controller controller;
    SharedToken sharedToken;
    Dialog dialog;
    ProjectAdapter adapter;
    @BindView(R.id.tooolbar)
    Toolbar tooolbar;
    public static ArrayList<ProjectApis.Datum> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        controller = new Controller(this);
        sharedToken = new SharedToken(this);
        dialog = ProgressBarClass.showProgressDialog(this);

        setSupportActionBar(tooolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setProjects(sharedToken.getUserId());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

    }

    @Override
    public void onSucess(Response<ProjectApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                data.clear();
                for (int i = 0; i < response.body().getData().size(); i++) {
                    data.add(response.body().getData().get(i));
                    setData(data);
                }

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


    //set data into reycelerView
    private void setData(ArrayList<ProjectApis.Datum> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProjectAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
