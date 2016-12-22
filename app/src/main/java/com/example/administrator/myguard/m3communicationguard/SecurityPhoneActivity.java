package com.example.administrator.myguard.m3communicationguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myguard.R;
import com.example.administrator.myguard.m3communicationguard.adapter.BlackContactAdapter;
import com.example.administrator.myguard.m3communicationguard.adapter.BlackContactAdapter.BlackContactCallBack;
import com.example.administrator.myguard.m3communicationguard.db.dao.BlackNumberDao;
import com.example.administrator.myguard.m3communicationguard.entity.BlackContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 16/12/19.
 */

public class SecurityPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout mHaveBlackNumber;
    private FrameLayout mNoBlackNumber;
    private BlackNumberDao dao;
    private ListView mlistView;
    private int pageumber =0;
    private int pagesize =15;
    private int totalNumber;
    private List<BlackContactInfo> pageBlackNumber = new ArrayList <BlackContactInfo>();
    private BlackContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_security_phone);
        initview();
        fillData();
    }

    private void fillData() {
        dao = new BlackNumberDao(SecurityPhoneActivity.this);
        totalNumber= dao.getTotalNumber();
        if(totalNumber==0){
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        }else if (totalNumber>0){
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
            pageumber=0;
            if(pageBlackNumber.size()>0){
                pageBlackNumber.clear();
            }
        pageBlackNumber.addAll(dao.getPageBlackNumber(pageumber,pagesize));
            if(adapter==null){
                adapter =new BlackContactAdapter(pageBlackNumber,SecurityPhoneActivity.this);
                adapter.setCallBack(new BlackContactCallBack(){
                    @Override
                    public void DataSizeChanged() {
                        fillData();
                    }


                });
                mlistView.setAdapter(adapter);
            }
                    adapter.notifyDataSetChanged();
        }
    }

    private void initview() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_purple)
        );
        ImageView mLeftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("通讯卫士");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mHaveBlackNumber=(FrameLayout) findViewById(R.id.fl_haveblacknumber);
        mNoBlackNumber =(FrameLayout) findViewById(R.id.fl_noblacknumber);
        findViewById(R.id.btn_addblacknumber).setOnClickListener(this);
        mlistView=(ListView) findViewById(R.id.lv_blacknumbers);
        mlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        int lastVisiblePosition = mlistView.getLastVisiblePosition();
                        if(lastVisiblePosition==pageBlackNumber.size()-1){
                            pageumber++;
                            if(pageumber * pagesize >=totalNumber){
                                Toast.makeText(SecurityPhoneActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                            }else{
                                pageBlackNumber.addAll(dao.getPageBlackNumber(
                                        pageumber,pagesize));
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        }

    @Override
    protected void onResume() {
        super.onResume();
        if(totalNumber!=dao.getTotalNumber()){
            if(dao.getTotalNumber()>0){
                mHaveBlackNumber.setVisibility(View.VISIBLE);
                mNoBlackNumber.setVisibility(View.GONE);
            }else{
                mHaveBlackNumber.setVisibility(View.GONE);
                mNoBlackNumber.setVisibility(View.VISIBLE);
            }
            pageumber=0;
            pageBlackNumber.clear();
            pageBlackNumber.addAll(dao.getPageBlackNumber(pageumber,pagesize));
            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case  R.id.btn_addblacknumber:
                startActivity(new Intent(this,AddBlackNumberActivity.class));
                break;
        }
    }
}
