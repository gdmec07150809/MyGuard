package com.example.administrator.myguard.m1home;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.myguard.R;
import com.example.administrator.myguard.m10settings.SettingsActivity;
import com.example.administrator.myguard.m1home.adapter.HomeAdapter;
import com.example.administrator.myguard.m2theftguard.LostFindActivity;
import com.example.administrator.myguard.m2theftguard.dialog.InterPasswordDialog;
import com.example.administrator.myguard.m2theftguard.dialog.SetUpPasswordDialog;
import com.example.administrator.myguard.m2theftguard.receiver.MyDeviceAdminReciever;
import com.example.administrator.myguard.m2theftguard.utils.MD5Utils;
import com.example.administrator.myguard.m3communicationguard.SecurityPhoneActivity;
import com.example.administrator.myguard.m4appmanager.AppManagerActivity;
import com.example.administrator.myguard.m5virusscan.VirScanActivity;
import com.example.administrator.myguard.m6cleancache.CacheClearListActivity;
import com.example.administrator.myguard.m7processmanager.ProcessManagerActivity;
import com.example.administrator.myguard.m8trafficmonitor.TrafficMonitoringActivity;
import com.example.administrator.myguard.m9advancedtools.AdvancedToolsActivity;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private SharedPreferences msharedPreferences;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private long mExitTime;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (isSetUpPassword()) {
                            showInterPswdDialog();
                        } else {
                            showSetUpPswdDialog();
                        }
                        break;
                    case 1:
                        startActivity(SecurityPhoneActivity.class);
                        break;
                    case 2:
                        startActivity(AppManagerActivity.class);
                        break;
                    case 3:
                        startActivity(VirScanActivity.class);
                        break;
                    case 4:
                        startActivity(CacheClearListActivity.class);
                        break;
                    case 5:
                        startActivity(ProcessManagerActivity.class);
                        break;
                    case 6:
                        startActivity(TrafficMonitoringActivity.class);
                        break;
                    case 7:
                        startActivity(AdvancedToolsActivity.class);
                        break;
                    case 8:
                        startActivity(SettingsActivity.class);
                        break;
                }
            }
        });

        policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, MyDeviceAdminReciever.class);
        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "获取超级管理员权限,用于远程锁屏和清除数据");
            startActivity(intent);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void showSetUpPswdDialog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) && TextUtils.isEmpty(affirmPwsd)) {
                    if (firstPwsd.equals(affirmPwsd)) {
                        savePwsd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        showInterPswdDialog();
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致!", 0).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", 0).show();
                }
            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }


    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog mInPswDialog = new InterPasswordDialog(HomeActivity.this);
        mInPswDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(mInPswDialog.getPassword())) {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", 0).show();
                } else if (password.equals(MD5Utils.encode(mInPswDialog.getPassword()))) {
                    mInPswDialog.dismiss();
                    startActivity(LostFindActivity.class);
                } else {
                    mInPswDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入！", 0).show();
                }
            }

            @Override
            public void cancle() {
                mInPswDialog.dismiss();
            }
        });
        mInPswDialog.setCancelable(true);
        mInPswDialog.show();


    }

    private void savePwsd(String affirmPwsd) {
        SharedPreferences.Editor edit = msharedPreferences.edit();
        edit.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        edit.commit();
    }

    public String getPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        return password;
    }

    public boolean isSetUpPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis()-mExitTime)>2000){
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }else{
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}



