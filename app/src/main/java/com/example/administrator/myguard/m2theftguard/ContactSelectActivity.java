package com.example.administrator.myguard.m2theftguard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myguard.R;
import com.example.administrator.myguard.m2theftguard.adapter.ContactAdapter;
import com.example.administrator.myguard.m2theftguard.entity.ContactInfo;
import com.example.administrator.myguard.m2theftguard.utils.ContactInfoParser;

import java.util.List;

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mListView;
    private ContactAdapter adapter;
    private List<ContactInfo> systemContacts;
    Handler mHandler=new Handler(){
        public void handleMwessage(android.os.Message msg){
        switch (msg.what){
            case 10:
                if(systemContacts!=null){
                    adapter=new ContactAdapter(systemContacts,ContactSelectActivity.this);
                    mListView.setAdapter(adapter);
                }
                break;
        }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_select);
        initView();
    }
    private void initView(){
        ((TextView)findViewById(R.id.tv_title)).setText("选择联系人");
        ImageView mLeftImgv= (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
        mListView= (ListView) findViewById(R.id.lv_contact);
        new Thread(){
            public void run(){
                systemContacts= ContactInfoParser.getSystemContact(ContactSelectActivity.this);
                systemContacts.addAll(ContactInfoParser.getSimContacts(ContactSelectActivity.this));
                mHandler.sendEmptyMessage(10);
            };
        }.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo item=(ContactInfo)adapter.getItem(position);
                Intent intent=new Intent();
                intent.putExtra("phone",item.phone);
                setResult(0,intent);
                finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.imgv_leftbtn:
            finish();
            break;
    }
    }
}