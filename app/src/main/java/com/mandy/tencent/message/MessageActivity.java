package com.mandy.tencent.message;


import android.icu.util.Calendar;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mandy.tencent.R;
import com.mandy.tencent.kotlin.Message;
import com.mandy.tencent.message.adapter.MessageAdapter;


import com.mandy.tencent.util.SharedToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.edtText)
    EditText edtText;
    @BindView(R.id.imgSend)
    ImageView imgSend;
    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    SharedToken sharedToken;

    //    final String MESSAGES_ENDPOINT = "https://us1.pusherplatform.io/services/chatkit_token_provider/v1/5cbfeb84-cd82-4fbb-9891-b6ed61d3eb38/token";
    final String MESSAGES_ENDPOINT = "http://pusher-chat-demo.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");


        getData();

        setMessage();

        edtText.setMovementMethod(new ScrollingMovementMethod());
    }


    @OnClick({R.id.imgAdd, R.id.imgSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAdd:
                break;
            case R.id.imgSend:
                sendData();
                break;
        }
    }

    private void setMessage() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("0");
        arrayList.add("1");
        arrayList.add("0");

        MessageAdapter adapter = new MessageAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void getData() {
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("806f8151ff6f92321e57", options);

        Channel channel = pusher.subscribe("chat");

        channel.bind("new_message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
            }
        });

        pusher.connect();

    }


    private void sendData() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Message message = new Message(sharedToken.getUserId(),
                    edtText.getText().toString(),
                    Calendar.getInstance().getTimeInMillis());

            com.pusher.rest.Pusher pusher = new com.pusher.rest.Pusher("868389", "806f8151ff6f92321e57", "06d94071e828322259cf");
            pusher.setCluster("eu");
            pusher.setEncrypted(true);

            pusher.trigger("chat", "new_message", message);
        }
    }


}
