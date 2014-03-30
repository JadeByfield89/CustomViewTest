package com.example.customviewtestapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.example.customviewtestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jade Byfield on 3/29/2014.
 */

//ImageView that draws a grid on top of it's canvas
public class GriddedImageView extends ImageView {


    //touch tools
    private static final int INVALID_POINTER_ID = -1;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private float mLastGestureX;
    private float mLastGestureY;

    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;


    private float mPosX;
    private float mPosY;
    private float mLastTouchX;
    private float mLastTouchY;


    //drawing tools
    private boolean mDrawMode;
    private Bitmap mBitmap = null;
    private int mNumRows;
    private int mNumColumns;
    private Paint mPaint;
    private boolean gridDrawn = false;
    private Path mPath;
    private Paint mBitmapPaint;
    private Paint mCirclePaint;
    private Path mCirclePath;
    private Canvas mCanvas;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Paint mDrawingPaint;
    private List<Path> mPaths = new ArrayList<Path>();
    private List<Paint> mPaints = new ArrayList<Paint>();
    private float mBrushSize = 12.0f;
    private int mCurrentPath;

    public GriddedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GriddedImageView, 0, 0);


        try {

            mNumRows = a.getInteger(R.styleable.GriddedImageView_numRows, 3);
            mNumColumns = a.getInteger(R.styleable.GriddedImageView_numColums, 3);

            //init drawing tools
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(4);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            a.recycle();
        }


        // Create our ScaleGestureDetector
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());


        //Sets up drawing tools
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mCirclePaint = new Paint();
        mCirclePath = new Path();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.CYAN);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeJoin(Paint.Join.MITER);
        mCirclePaint.setStrokeWidth(4f);


        //


        mDrawingPaint = new Paint();

        mDrawingPaint.setAntiAlias(true);
        mDrawingPaint.setDither(true);
        mDrawingPaint.setColor(Color.GREEN);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeWidth(mBrushSize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //mCanvas = new Canvas(mBitmap);
    }

    public GriddedImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCanvas = canvas;

        int width = canvas.getWidth() / 3;
        int height = canvas.getHeight() / 3;
        // float mColumnOffset = width / mNumColumns;
        //float mRowOffset = height / mNumRows;

        //Canvas is not in draw mode, draw the image and allow scaling/zooming
        if (!mDrawMode) {

            canvas.save();
            //
            // canvas.translate(mPosX, mPosY);
            canvas.scale(mScaleFactor, mScaleFactor);


            //canvas.drawPoint(mPosX, mPosY, mPaint);

            if (mBitmap != null && canvas != null) {
                canvas.drawBitmap(mBitmap, mPosX, mPosY, mPaint);
            }


            drawGrid2(canvas, width, height);
            canvas.restore();
        }

        //Canvas is in draw mode, draw the bitmap and the grid
        //Just don't allow scaling
        else {


            if (mBitmap != null && canvas != null) {

                canvas.drawBitmap(mBitmap, mPosX, mPosY, mPaint);
                drawGrid2(canvas, width, height);
            }
            // canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);


            for (int i = 0; i < mPaths.size(); i++) {

                canvas.drawPath(mPaths.get(i), mPaints.get(i));

            }

            canvas.drawPath(mCirclePath, mCirclePaint);

        }


    }

    private void touch_start(float x, float y) {
        //Path newPath = new Path();
        // newPath.moveTo(x, y);


        mPath = new Path();
        mPath.moveTo(x, y);

        mPaths.add(mPath);

        //create a new Paint for each new path
        mDrawingPaint = new Paint();
        mDrawingPaint.setAntiAlias(true);
        mDrawingPaint.setDither(true);
        mDrawingPaint.setColor(Color.GREEN);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeWidth(mBrushSize);
        mPaints.add(mDrawingPaint);
        //mCurrentPath = mPaths.indexOf(mPath);


        //mPath.reset();


        mX = x;
        mY = y;
    }


    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCirclePath.reset();
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath.reset();
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            mCirclePath.reset();
            mCirclePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }


    public void setDrawMode(boolean draw) {

        this.mDrawMode = draw;
    }

    public boolean getDrawMode() {

        return mDrawMode;
    }

    private void drawGrid(Canvas canvas, int width, int height) {

        // Draw the minor grid lines
        //if(!gridDrawn) {

        //gridDrawn = true;

           /* for (int i = 0; i < 9; i++) {
                canvas.drawLine(0, i * height, getWidth(), i * height,
                        mPaint);
                canvas.drawLine(0, i * height + 1, getWidth(), i * height
                        + 1, mPaint);
                canvas.drawLine(i * width, 0, i * width, getHeight(),
                        mPaint);
                canvas.drawLine(i * width + 1, 0, i * width + 1,
                        getHeight(), mPaint);
            }
*/

        // Draw the major grid lines
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0)
                continue;
            canvas.drawLine(0, i * height, getWidth(), i * height,
                    mPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height
                    + 1, mPaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), mPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1,
                    getHeight(), mPaint);
        }
        //}


    }


    private void drawGrid2(Canvas canvas, int width, int height) {

        canvas.drawLine(width, 0, width, canvas.getHeight(), mPaint);
        canvas.drawLine(2 * width, 0, 2 * width, canvas.getHeight(), mPaint);
        canvas.drawLine(0, height, canvas.getWidth(), height, mPaint);
        canvas.drawLine(0, height * 2, canvas.getWidth(), height * 2, mPaint);
    }

    public void setBrushSize(float size) {

        this.mBrushSize = size;
        //mDrawingPaint = new Paint();
        //mPaints.add(mDrawingPaint);


       // mDrawingPaint.setStrokeWidth(mBrushSize);*/
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        //super.setImageBitmap(bm);

        if (bm != null) {

            mBitmap = bm;
        }
    }

    public void removePath() {

        //remove the most recent path from the list
        try {
            mPaths.remove(mPaths.size() - 1);
            mCirclePath.reset();
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearDrawing() {

        mPaths.clear();
        mCirclePath.reset();
        invalidate();
    }

    private void setNumRows(int numRows) {

        this.mNumRows = numRows;
    }

    private int getNumRows() {

        return mNumRows;
    }

    private void setNumColumns(int numColumns) {

        this.mNumColumns = numColumns;
    }

    private int getNumColumns() {

        return this.mNumColumns;
    }


    //Listen for multi-touch drag event and redraw the view accordingly
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(event);


        final int action = event.getAction();
        switch (action) {

            //a touch down
            case MotionEvent.ACTION_DOWN: {
                //Scale detector is not in progress
                if (!mScaleDetector.isInProgress()) {
                    final float x = event.getX();
                    final float y = event.getY();
                    //Save the ID of this pointer
                    mActivePointerId = event.getPointerId(0);

                    if (mDrawMode) {

                        touch_start(x, y);
                        invalidate();
                    }


                        //Remember where we started
                        mLastTouchX = x;
                        mLastTouchY = y;


                        //Save the ID of this pointer
                        mActivePointerId = event.getPointerId(0);


                        break;

                }
            }

            case MotionEvent.ACTION_MOVE: {

                //Only move the image if the scale detector is not in progress
                if (!mScaleDetector.isInProgress()) {
                    //Find the index of active pointer and save its position
                    final int pointerIndex = event.findPointerIndex(mActivePointerId);
                    final float x = event.getX(pointerIndex);
                    final float y = event.getY(pointerIndex);

                    if (mDrawMode) {

                        touch_move(x, y);
                        invalidate();
                    } else {
                        //Calculate the distance moved
                        float dx = x - mLastTouchX;
                        float dy = y - mLastTouchY;


                        //Move the object
                          mPosX += dx;
                          mPosY += dy;


                        //Remember this touch position for the next move event
                        mLastTouchX = x;
                        mLastTouchY = y;


                        //Invalidate to request a redraw
                        invalidate();
                    }
                    // break;

                } else {

                    final float gx = mScaleDetector.getFocusX();
                    final float gy = mScaleDetector.getFocusY();

                    final float gdx = gx - mLastGestureX;
                    final float gdy = gy - mLastGestureY;

                    mPosX += gdx;
                    mPosY += gdy;

                    invalidate();

                    mLastGestureX = gx;
                    mLastGestureY = gy;

                }

                break;
            }
            case MotionEvent.ACTION_UP: {

                //Reset the active pointer id
                mActivePointerId = INVALID_POINTER_ID;

                if (mDrawMode) {

                    //touch_up();
                    //invalidate();
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL: {

                mActivePointerId = INVALID_POINTER_ID;
                break;

            }

            case MotionEvent.ACTION_POINTER_UP: {
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;

                    if (event.getPointerCount() >= 2) {
                        mLastTouchX = event.getX(newPointerIndex);
                        mLastTouchY = event.getY(newPointerIndex);
                    }
                    mActivePointerId = event.getPointerId(newPointerIndex);


                } else {
                    final int tempPointerIndex = event.findPointerIndex(mActivePointerId);
                    mLastTouchX = event.getX(tempPointerIndex);
                    mLastTouchY = event.getY(tempPointerIndex);
                }
                break;

            }

        }


        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();


            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(1.f, Math.min(mScaleFactor, 5.0f));

            invalidate();


            return true;
        }
    }


}
