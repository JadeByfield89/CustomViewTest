package com.example.customviewtestapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.customviewtestapp.R;

/**
 * Created by Jade Byfield on 3/28/2014.
 */
public class BubbleView extends View {


    private int mRadius;
    private int mColor;
    private Paint mPaint;
    private float mCenter;
    private Animation mAnimation;




    public BubbleView(Context context){
        super(context);
    }


    public BubbleView(Context context, AttributeSet attrs){
        super(context, attrs);

        //Obtain typedarray of styleattributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BubbleView, 0, 0);


        //Get set attributes that will be used for drawing this view
        try {

            mRadius = a.getInteger(R.styleable.BubbleView_circleRadius, 10);
            mColor = a.getInteger(R.styleable.BubbleView_circleColor, Color.parseColor("#eeeeee"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        finally{
            a.recycle();
        }


        //load the animation
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.view_bounce);
        //drawing tools



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setColor(mColor);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, mRadius, mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //the bubble was touched
        if(event.getAction() == MotionEvent.ACTION_DOWN){

           startAnimation(mAnimation);
        }
        return super.onTouchEvent(event);
    }

    public int getBubbleRadius(){

        return mRadius;



    }

    public void setRadius(int radius){

        this.mRadius = radius;
    }

    public int getColor(){

        return mColor;
    }

    public void setColor(int color){

        this.mColor = color;
    }





}
