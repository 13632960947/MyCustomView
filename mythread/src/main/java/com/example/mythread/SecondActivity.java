package com.example.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity {
    boolean isStart=false;
    MyThread myThread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button=findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart=true;
                myThread=new MyThread();
               myThread.start();

            }
        });
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (isStart){
                Log.e(SecondActivity.class.getSimpleName(),"--运行中");
                if(!isStart){
                    break;
                }
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart=false;
        myThread.interrupt();

    }
}
