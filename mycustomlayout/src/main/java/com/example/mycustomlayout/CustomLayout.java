package com.example.mycustomlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CustomLayout extends RelativeLayout {
    View view;
    Button button01;
    public CustomLayout(Context context) {
        super(context);
        view=LayoutInflater.from(context).inflate(R.layout.itemview_layout,this);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        view=LayoutInflater.from(context).inflate(R.layout.itemview_layout,this);
        button01=view.findViewById(R.id.btn_fine);
        button01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"我是中国人",Toast.LENGTH_LONG).show();
                button01.setText("hah");
                invalidate();
            }
        });
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view=LayoutInflater.from(context).inflate(R.layout.itemview_layout,this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
