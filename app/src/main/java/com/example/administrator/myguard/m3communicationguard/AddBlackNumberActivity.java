package com.example.administrator.myguard.m3communicationguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myguard.R;
import com.example.administrator.myguard.m3communicationguard.db.dao.BlackNumberDao;
import com.example.administrator.myguard.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by student on 16/12/21.
 */

public class AddBlackNumberActivity extends AppCompatActivity implements View.OnClickListener{
    private CheckBox mSmsCB;
    private CheckBox mTelCB;
    private EditText mNumET;
    private EditText mNameET;
    private BlackNumberDao dao;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_black_number);
        dao=new BlackNumberDao(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_purple)
        );
        ((TextView) findViewById(R.id.tv_title)).setText("添加黑名单");
        ImageView mLeftImgv =(ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);

        mSmsCB = (CheckBox) findViewById(R.id.cb_blacknumber_sms);
        mTelCB = (CheckBox) findViewById(R.id.cb_blacknumber_tel);
        mNumET = (EditText) findViewById(R.id.et_blacknumber);
        mNameET = (EditText) findViewById(R.id.et_blackname);
        findViewById(R.id.add_blacknum_btn).setOnClickListener(this);
        findViewById(R.id.add_fromcontact_btn).setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.add_blacknum_btn:
                String number = mNumET.getText().toString().trim();
                String name = mNameET.getText().toString().trim();
                if(TextUtils.isEmpty(number)||TextUtils.isEmpty(name)){
                Toast.makeText(this, "电话号码和手机号不能为空",Toast.LENGTH_SHORT).show();
                return;

            }else{
                BlackContactInfo blackContantInfo =new BlackContactInfo();
                blackContantInfo.phoneNumber = number;
                blackContantInfo.contactName =name;
                if(mSmsCB.isChecked()& mTelCB.isChecked()){
                    blackContantInfo.mode =3;

                }else if(mSmsCB.isChecked()& !mTelCB.isChecked()){
                    blackContantInfo.mode =2;
                }else if(!mSmsCB.isChecked()& mTelCB.isChecked()){
                    blackContantInfo.mode =1;
                }else{
                    Toast.makeText(this, "请选择拦截模式", Toast.LENGTH_SHORT).show();
                    return;
                }
            if(!dao.IsNumberExist(blackContantInfo.phoneNumber)){
                dao.add(blackContantInfo);
            }else{
                Toast.makeText(this, "该号码已经被添加到黑名单", Toast.LENGTH_SHORT).show();
            }
                finish();
            }
            break;
            case R.id.add_fromcontact_btn:
                startActivityForResult(
                        new Intent(this,ContactSelectActivity.class),0);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode,requestCode,data);
        if(data!=null){
            String phone =data.getStringExtra("phone");
            String name = data.getStringExtra("name");
            mNameET.setText(name);
            mNumET.setText(phone);
        }
    }
}
