package com.example.mycustomlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

public class CustomView extends View {
    private float xCenter;
    private float yCenter;
    private float radius;
    private Paint paint;
    private Paint paint1;
    private Paint paintBall;
    private Canvas canvas;
    private float current;
    private float ballRaidus;
    private float ballX;
    private float ballY;
    private ValueAnimator  valueAnimator;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int heigth=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.AT_MOST){
            width=DestyUtil.getPingMU(getContext()).widthPixels/2;
        }
        if(heightMode==MeasureSpec.AT_MOST){
            heigth=DestyUtil.getPingMU(getContext()).widthPixels/2;
        }
        xCenter=width/2;
        yCenter=heigth/2;
        radius=width/2-20;
        setMeasuredDimension(width,heigth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasView(canvas);
        this.canvas=canvas;
    }

    private void initView(){
         paint=new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);

        paint1=new Paint();
        paint1.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20f);
        paint1.setAntiAlias(true);

        paintBall=new Paint();
        paintBall.setAntiAlias(true);
        paintBall.setColor(getResources().getColor(R.color.colorPrimary));
        ballRaidus=20f;
        ballX=xCenter;
        ballY=yCenter+radius;

    }

    private void canvasView(Canvas canvas){
        canvas.drawCircle(xCenter,yCenter,radius,paint);
        RectF oval=new RectF(10,10,xCenter+radius,yCenter+radius);
//        canvas.drawArc(oval,90,360*current/100,false,paint1);
        canvas.drawCircle(ballX,ballY,ballRaidus,paintBall);
    }

    public void setPercent(int max){
//        setCurrentView(max);
        setAnimator(max);
    }


    private void setCurrentView(final int current){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<=current;i++){
                        Thread.sleep(300);
                        CustomView.this.current=i;
                        CustomView.this.postInvalidate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void setAnimator(final int mCurrent){
        valueAnimator=ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currenA= (float) animation.getAnimatedValue();
                CustomView.this.current=mCurrent*currenA;
                setBallXY(CustomView.this.current);
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setDuration(30000);
        valueAnimator.start();
    }


    public void stopAnimator(){
        valueAnimator.end();
        valueAnimator.cancel();
        current=0;
        setBallXY(current);
        invalidate();
    }


    private void setBallXY(float currentRadus){
        ballX= (float) (xCenter+radius*Math.sin(currentRadus));
        ballY= (float) (yCenter+radius*Math.cos(currentRadus));
    }
}
