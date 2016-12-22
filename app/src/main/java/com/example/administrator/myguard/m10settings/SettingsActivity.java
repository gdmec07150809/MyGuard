package com.example.administrator.myguard.m10settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myguard.R;
import com.example.administrator.myguard.m10settings.widget.SettingView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener,SettingView.OnCheckedStatusIsChanged{
private SettingView mBlackNumSV;
    private SettingView mAppLockSV;
    private SharedPreferences mSP;
    private boolean running;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        initView();
    }
    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_blue));
        ImageView mLeftImgv= (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("设置中心");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mBlackNumSV=findViewById();
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(View view, boolean isChecked) {

    }
}
