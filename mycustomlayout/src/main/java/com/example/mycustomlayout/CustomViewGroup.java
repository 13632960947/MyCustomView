package com.example.mycustomlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomViewGroup extends ViewGroup implements View.OnClickListener{
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childcount=getChildCount();
        int mLeft=0;
        int mNextLeft=0;
        for(int i=0;i<childcount;i++){
            View childView=getChildAt(i);
            int childViewHeight=childView.getMeasuredHeight();
            int childViewWidth=childView.getMeasuredWidth();

            int height=0;
            if(i<5) {
                childView.layout(mLeft, height, mLeft + childViewWidth, height + childViewHeight);
                mLeft += childViewWidth;
            }else{

                childView.layout(mNextLeft, height+childViewHeight, mNextLeft + childViewWidth, height + childViewHeight*2);
                mNextLeft += childViewWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childcount=getChildCount();
        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);
        int modeHeigth=MeasureSpec.getMode(heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(modeWidth==MeasureSpec.AT_MOST){
            width=DestyUtil.getPingMU(getContext()).widthPixels;
        }
        if(modeHeigth==MeasureSpec.AT_MOST){
            height=DestyUtil.getPingMU(getContext()).heightPixels;
        }
        for(int i=0;i<childcount;i++){
            int widthSize=0;
            int heightSize=height;
            int fistWidthSize=200;

            if(i==0){
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(fistWidthSize,MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.AT_MOST));
            }else if(i<5){
                widthSize=(width-200)/4;
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
            }else{
                widthSize=width/5;
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
            }
//            measureChildren(width,height);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<getChildCount();i++) {
            getChildAt(i).setOnClickListener(this);
            getChildAt(i).setId(i);
        }
    }



    interface OnClickListener{
        void setTagNumner(int id);
    }

    OnClickListener onClickListener;
    public void setClickListener(OnClickListener onClickListener){
       this.onClickListener=onClickListener;
    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null)
        onClickListener.setTagNumner(v.getId());
    }


    public void setTextList(){
        int childcount=getChildCount();
        for(int i=0;i<childcount;i++){
            Button button= (Button) getChildAt(i);
            button.setText("hha");
        }
    }
}
