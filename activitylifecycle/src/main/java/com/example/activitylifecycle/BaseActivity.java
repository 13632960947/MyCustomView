package com.example.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public  abstract class BaseActivity extends Activity {
    private String destroty="onDestroy";
    private String create="onCreate";
    private View contentView;
    private FrameLayout layoutTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CLog.e(BaseActivity.class.getSimpleName(),create);
        setContentView(getLayoutID());
        contentView=LayoutInflater.from(this).inflate(getLayoutID(),null);
        initView(contentView);
        LinearLayout constraintLayout=findViewById(R.id.layout_root);
        ConstraintLayout.LayoutParams layoutParams=new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.bottomToBottom=10;

        ImageView imageView=new ImageView(this);
        imageView.setTag(1);
        imageView.setOnClickListener(onClickListener);

        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.mipmap.ic_launcher);
        constraintLayout.addView(imageView);
        initTitle();
    }

    private void initTitle(){
        ImageView button=findViewById(R.id.backIcon);
        button.setOnClickListener(onClickListener);
    }

    public View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch ((int)v.getTag()){
                case 1:
                    Toast.makeText(BaseActivity.this,"公共点击",Toast.LENGTH_SHORT).show();
                    break;
            }
            switch (v.getId()){
                case R.id.backIcon:
                    finish();
                    break;
            }
        }
    };

    public class MyOnClickter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch ((int)v.getTag()){
                case 1:
                    Toast.makeText(BaseActivity.this,"公共点击",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        CLog.e(BaseActivity.class.getSimpleName(),destroty);
        super.onDestroy();
    }

    public abstract int getLayoutID();

    public abstract void initView(View contentView);
}
