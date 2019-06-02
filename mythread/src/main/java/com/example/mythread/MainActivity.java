package com.example.mythread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.btn_skip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
//                startActivity(intent);
                setThreadPools();
            }
        });
    }

    private void setThreadPools(){
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<10;i++){
                        Thread.sleep(5000);
                        Log.e(MainActivity.class.getSimpleName(),"--"+i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
