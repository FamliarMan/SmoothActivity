package com.jianglei.smoothactivity.sample;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jianglei.smoothatyoperator.JlActivity;
import com.jianglei.smoothatyoperator.OnPermissionResultListener;
import com.jianglei.smoothatyoperator.SmoothAtyOperator;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        findViewById(R.id.btn_start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothAtyOperator.prepareActivity()
                        //将参数设置到intent中
                        .putExtraToIntent("intent", "test intent")
                        //将参数设置到bundle中
                        .putStringToBundle("bundle", "test bundle")
                        //如果觉得默认的方法不够，可以设置自己想要的intent
                        //.intent(new Intent()) 如果觉得提供
                        //如果觉得默认的方法不够，可以设置自己想要的bundle
                        //.bundle(new Bundle())
                        .build()
                        .startActivity(MainActivity.this, JumpTargetActivity.class);
            }
        });
        findViewById(R.id.btn_start_activity_for_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothAtyOperator.prepareActivity()
                        .putExtraToIntent("intent", "test intent")
                        .putStringToBundle("bundle", "test bundle")
                        .build()
                        .startActivityForResult(MainActivity.this,
                                JumpTargetActivity.class,
                                new JlActivity.OnActivityResultListener() {
                                    @Override
                                    public void onResultOk(Intent data) {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.app_get_return,
                                                        data.getStringExtra("intent")),
                                                Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onResultCancel() {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.app_return_cancel),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
            }
        });
    }

    private void requestPermission() {
        SmoothAtyOperator.startPermission(this)
                .addPermission(Manifest.permission.WRITE_CONTACTS)
                .addPermission(Manifest.permission.CAMERA)
                .build()
                .request(new OnPermissionResultListener() {
                    @Override
                    public void onGranted(String[] permissions) {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.app_ask_permission_success,
                                        Arrays.toString(permissions)),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(String[] permissions) {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.app_ask_permission_fail,
                                        Arrays.toString(permissions)),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
}
