package com.jianglei.smoothactivity.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JumpTargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_target);
        TextView tvContent = findViewById(R.id.tv_content);
        Intent intent = getIntent();
        String intentParam = intent.getStringExtra("intent");
        String bundleParam = intent.getExtras().getString("bundle");
        tvContent.setText(getString(R.string.app_get_param, intentParam, bundleParam));

        findViewById(R.id.btn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("intent", "return param");
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
