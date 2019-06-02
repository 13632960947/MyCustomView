package com.example.blockcanary;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn_close);
        imitateClick();
    }

    public void imitateClick() {
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                  Thread.sleep(20*1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              Toast toast = Toast.makeText(getApplicationContext(),"点击完成", Toast.LENGTH_LONG);
              toast.show();
          }

      });
    }
}
