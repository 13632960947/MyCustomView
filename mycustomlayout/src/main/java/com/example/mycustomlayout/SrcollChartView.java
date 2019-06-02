package com.example.mycustomlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class SrcollChartView extends View {
    private Paint paintChart;
    private Paint paintLine;
    private Paint paintMLine;
    private int width;
    private int height;
    private float startChartX;
    private float startChartY;
    private float stopChartY;
    private float stopChartX;
    private float startXData;
    private float  startYData;
    private float startY;
    private float maxY;
    private float maxX;
    private float xArrowLength;
    private float yArrowLength;
    private List<DataChartBean> list;
    private int xCount = 3;
    private int yCount = 5;
    private float bMargin;
    private float sMargin;

    public SrcollChartView(Context context) {
        super(context);
    }

    public SrcollChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SrcollChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(List<DataChartBean> list, float maxX, float maxY) {
        this.list = list;

        this.maxX = maxX;
        this.maxY = maxY;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = DestyUtil.getPingMU(getContext()).widthPixels;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = DestyUtil.getPingMU(getContext()).heightPixels / 2;
        }
        startChartX = 30f;
        stopChartX = width - 10f;
        startChartY = height - 80f;
        stopChartY = 10f;
        yArrowLength = startChartY - stopChartY;
        xArrowLength = stopChartX - startChartX;
        bMargin=(stopChartX-startChartX)/xCount;
        sMargin=bMargin/5;
        setMeasuredDimension(width, height);
    }

    private void initPaint() {
        paintChart = new Paint();
        paintChart.setAntiAlias(true);
        paintChart.setColor(getResources().getColor(R.color.colorPrimary));
        paintChart.setStrokeWidth(5f);
        paintChart.setTextSize(50f);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(5f);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(getResources().getColor(R.color.colorAccent));

        paintMLine=new Paint();
        paintMLine.setAntiAlias(true);
        paintMLine.setStrokeWidth(5f);
        paintMLine.setColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChart(canvas);
    }

    private void drawChart(Canvas canvas) {

        canvas.drawLine(startChartX, startChartY, stopChartX, startChartY, paintChart);
        canvas.drawLine(startChartX, startChartY, startChartX, stopChartY, paintChart);
        for (int i = 0; i < yCount; i++) {
            canvas.drawText(String.valueOf(maxY * i / yCount), startChartX+20, startChartY - (yArrowLength) * i / yCount-20, paintChart);
        }
        for (int i = 0; i <= xCount*2; i++) {

            for(int j=0;j<5;j++) {
                if(i==0&&j==0){
                    continue;
                }
                float xPostion=startXData+startChartX+bMargin*i+sMargin*j;
                if(xPostion>startChartX) {
                    if (j == 0) {
                        canvas.drawLine(xPostion, startChartY, xPostion, startChartY - 40, paintMLine);
                    } else {
                        canvas.drawLine(xPostion, startChartY, xPostion, startChartY - 20, paintMLine);
                    }
                }
            }
            canvas.drawText(String.valueOf((int) (maxX * i / xCount)), startXData+startChartX + (xArrowLength) * i / xCount, startChartY + 60f, paintChart);
        }
        Path path = new Path();
        for (int i = 0; i <= xCount*2; i++) {
            for(int j=0;j<5;j++) {
                float xPostion = startXData + startChartX + bMargin * i + sMargin * j;
                if (xPostion >= startChartX) {
                    path.moveTo(xPostion, startChartY - list.get(i*5+j).getyData() - startYData);
                    break;
                }
            }
        }
        for (int i = 0; i <= xCount; i++) {
            for(int j=0;j<5;j++) {
                float xPostion = startXData + startChartX + bMargin * i + sMargin * j;
                if (xPostion >= startChartX) {
                    path.lineTo(xPostion, startChartY - list.get(i*5+j).getyData() - startYData);
                }
            }
        }
        canvas.drawPath(path, paintLine);
    }

    float startXP = 0;
    float startYP;
    float marginX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startXP = event.getX();
                startYP = event.getY();
                Log.e("--startXP" + SrcollChartView.class.getSimpleName(), String.valueOf(event.getX()));

                break;
            case MotionEvent.ACTION_MOVE:
                marginX = event.getX() - startXP;
                if (marginX > 50) {
                    startXData += 20;
//                    if (startYData < maxY ) {
//                        startXData += 50;
////                        startYData += 50;
//
//                    }
                } else if (startXP - event.getX() > 50) {
//                    if (startYData > 0) {
//                        startXData -= 50;
////                        startYData -= 50;
//                    }

                    startXData -= 20;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//
                break;
        }
        return true;
    }
}
