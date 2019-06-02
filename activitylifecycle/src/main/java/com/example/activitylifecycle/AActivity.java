package com.example.activitylifecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AActivity extends BaseActivity {
    private String destroty="onDestroy";
    private String create="onCreate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CLog.e(AActivity.class.getSimpleName(),create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CLog.e(AActivity.class.getSimpleName(),destroty);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_aa;
    }

    @Override
    public void initView(View contentView) {
        TextView tv=findViewById(R.id.tv_title);
        tv.setText("第二页");
//        FrameLayout layoutTitle=findViewById(R.id.layout_title);
//        layoutTitle.setVisibility(View.GONE);
    }


}
