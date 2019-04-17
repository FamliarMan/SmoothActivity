package com.jianglei.smoothactivity.sample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
