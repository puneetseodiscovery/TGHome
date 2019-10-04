package com.mandy.tencent.rooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mandy.tencent.MainActivity;
import com.mandy.tencent.R;
import com.mandy.tencent.uploadimages.UploadImageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomsActivity extends AppCompatActivity {

    @BindView(R.id.txtRoom)
    TextView txtRoom;
    @BindView(R.id.seekRoom)
    SeekBar seekRoom;
    @BindView(R.id.txtm2)
    TextView txtm2;
    @BindView(R.id.seekm2)
    SeekBar seekm2;
    @BindView(R.id.switch1)
    SwitchCompat switch1;
    @BindView(R.id.switch2)
    SwitchCompat switch2;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.txtBack)
    TextView txtBack;
    String room = "0", m2 = "0", radio1 = "0", radio2 = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        ButterKnife.bind(this);


        seekRoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtRoom.setText("" + progress);
                room = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekm2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtm2.setText("" + progress);
                m2 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio1 = "1";
                } else {
                    radio1 = "0";
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio2 = "1";
                } else {
                    radio2 = "0";
                }
            }
        });


    }

    @OnClick({R.id.btnSubmit, R.id.txtBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                Intent intent1 = new Intent(RoomsActivity.this, UploadImageActivity.class);
                intent1.putExtra("room", room);
                intent1.putExtra("m2", m2);
                intent1.putExtra("radio1", radio1);
                intent1.putExtra("radio2", radio2);
                startActivity(intent1);
                break;
            case R.id.txtBack:
                onBackPressed();
                break;
        }
    }
}
