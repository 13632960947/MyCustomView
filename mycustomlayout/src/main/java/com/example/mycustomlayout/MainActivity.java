package com.example.mycustomlayout;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_chart);
        initView03();

    }

    private void initView(){
        final CustomViewGroup customViewGroup=findViewById(R.id.customview01);
        CustomViewGroup customViewGroup2=findViewById(R.id.customview02);
        Button btnClick=findViewById(R.id.btn_refrsh);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewGroup.setTextList();
            }
        });
        for(int i=0;i<5;i++) {
            Button button = new Button(this);
            button.setText("测试"+i);
            customViewGroup.addView(button);
        }
        for(int i=0;i<5;i++) {
            Button button = new Button(this);
            button.setText("第二"+i);
            customViewGroup2.addView(button);
        }

        customViewGroup.setClickListener(new CustomViewGroup.OnClickListener() {
            @Override
            public void setTagNumner(int id) {
                Toast.makeText(MainActivity.this,String.valueOf(id),Toast.LENGTH_SHORT).show();
            }
        });
        customViewGroup2.setClickListener(new CustomViewGroup.OnClickListener() {
            @Override
            public void setTagNumner(int id) {
                Toast.makeText(MainActivity.this,String.valueOf(id),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView02(){
        final CustomView customView=findViewById(R.id.customView);

        findViewById(R.id.btn_refrsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setPercent(100);
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.stopAnimator();
            }
        });
    }


    private void initView03(){
        SrcollChartView srcollChartView=findViewById(R.id.chart_view);
        List<DataChartBean> listData=new ArrayList<>();
        for(int i=0;i<50;i++){
            DataChartBean dataChartBean=new DataChartBean();
            dataChartBean.setxData(i*5);
            dataChartBean.setyData(i*40);
            listData.add(dataChartBean);
        }
        srcollChartView.setData(listData,listData.get(49).getxData(),listData.get(49).getyData());

    }
}
