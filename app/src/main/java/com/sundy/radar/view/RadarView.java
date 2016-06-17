package com.sundy.radar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.sundy.radar.R;

/**
 * 雷达视图
 */
public class RadarView extends View {
    private int screenWidth=0;
    private  int screenHeight=0;
    private Paint mPaintNormal;	// 绘制普通圆圈和线的画笔
    private Paint mPaintCircle;// 绘制渐变圆
    private int normalColor=Color.parseColor("#A1A1A1");
    private int gradualColor=0x9D00ff00;
    //用矩阵实现旋转
    private Matrix matrix;
    private int start=0;
    private Handler handler=new Handler();

    private Runnable r=new Runnable() {
        @Override
        public void run() {
            start=start+2;
            matrix=new Matrix();
            matrix.postRotate(start,screenWidth/2,screenHeight/2);//设置画布旋转
            invalidate();//重绘
            handler.postDelayed(r,20);
        }
    };
    public RadarView(Context context) {
        this(context,null);

    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public RadarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        setBackgroundResource(R.mipmap.bg);
        this.screenWidth=context.getResources().getDisplayMetrics().widthPixels;
        this.screenHeight=context.getResources().getDisplayMetrics().heightPixels;
        handler.post(r);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         setMeasuredDimension(screenWidth,screenHeight);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPaintNormal=new Paint();
        mPaintNormal.setColor(normalColor);
        mPaintNormal.setAntiAlias(true);
        mPaintNormal.setStrokeWidth(3);
        mPaintNormal.setStyle(Paint.Style.STROKE);

        mPaintCircle=new Paint();
        mPaintCircle.setColor(gradualColor);
        mPaintCircle.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(screenWidth/2, screenHeight/2, screenWidth/6, mPaintNormal);		// 绘制小圆
        canvas.drawCircle(screenWidth/2, screenHeight/2, 2*screenWidth/6, mPaintNormal);	// 绘制中圆
        canvas.drawCircle(screenWidth/2, screenHeight/2, 11*screenWidth/20, mPaintNormal);	// 绘制中大圆
        canvas.drawCircle(screenWidth/2, screenHeight/2, 7*screenHeight/16, mPaintNormal);	// 绘制大圆
        //绘制简便的圆
        Shader shader=new SweepGradient(screenWidth/2,screenHeight/2,Color.TRANSPARENT, Color.parseColor("#AAAAAAAA"));
        mPaintCircle.setShader(shader);
        canvas.concat(matrix);
        canvas.drawCircle(screenWidth/2, screenHeight/2, 7*screenHeight/16,mPaintCircle);
        super.onDraw(canvas);
    }

}
