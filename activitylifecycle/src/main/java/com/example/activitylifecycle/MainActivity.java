package com.example.activitylifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private String destroty="onDestroy";
    private String create="onCreate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CLog.e(MainActivity.class.getSimpleName(),create);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CLog.e(MainActivity.class.getSimpleName(),destroty);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View contentView) {
         Button button=findViewById(R.id.next);
         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AActivity.class);
                startActivity(intent);
            }
        });
        TextView tv=findViewById(R.id.tv_title);
        tv.setText("第一页");
    }


}
