package com.example.customviewtestapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.customviewtestapp.R;
import com.example.customviewtestapp.views.GriddedImageView;

/**
 * Created by Jade Byfield on 3/29/2014.
 */
public class GriddedImageViewFragment extends Fragment implements View.OnClickListener{

    private GriddedImageView mZoomImage;
    private ImageButton first, second, third, fourth;
    private Animation mAnim;
    private Button bRemove, bClear, bDraw;
    private boolean mDrawMode;

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.bRemovePath:

                mZoomImage.removePath();
                break;

            case R.id.bClear:

                mZoomImage.clearDrawing();
                break;

            case R.id.ibBrush1:

                first.setBackgroundResource(R.drawable.brush_selected);
                //first.setBackground(getResources().getDrawable(R.drawable.brush_selected));
                mZoomImage.setBrushSize(12.0f);
                break;

            case R.id.ibBrush2:

                second.setBackgroundResource(R.drawable.brush_two_selected);
                //first.setBackground(getResources().getDrawable(R.drawable.brush_two_selected));
                mZoomImage.setBrushSize(18.0f);

                break;

            case R.id.ibBrush3:

                third.setBackgroundResource(R.drawable.brush_three_selected);
               // first.setBackground(getResources().getDrawable(R.drawable.brush_three_selected));
                mZoomImage.setBrushSize(24.0f);
                break;
            case R.id.ibBrush4:

                fourth.setBackgroundResource(R.drawable.brush_four_selected);
               // first.setBackground(getResources().getDrawable(R.drawable.brush_four_selected));
                mZoomImage.setBrushSize(30.0f);
                break;

            case R.id.bDrawMode:

                if(!mDrawMode){

                    mDrawMode = true;
                    bDraw.setText("Draw mode ON");
                    mZoomImage.setDrawMode(true);
                }
                else {
                    mDrawMode = false;
                    bDraw.setText("Draw mode OFF");
                }


                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.gridded_imageview_fragment, container, false);

        mZoomImage = (GriddedImageView) v.findViewById(R.id.ivZoomImage);
        first = (ImageButton)v.findViewById(R.id.ibBrush1);
        second = (ImageButton)v.findViewById(R.id.ibBrush2);
        third = (ImageButton)v.findViewById(R.id.ibBrush3);
        fourth = (ImageButton)v.findViewById(R.id.ibBrush4);
        bRemove = (Button)v.findViewById(R.id.bRemovePath);
        bClear = (Button)v.findViewById(R.id.bClear);
        bDraw = (Button)v.findViewById(R.id.bDrawMode);

        //on screen buttons
        bRemove.setOnClickListener(this);
        bClear.setOnClickListener(this);
        bDraw.setOnClickListener(this);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fourth.setOnClickListener(this);

        //animation
        mAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.view_bounce);
        first.startAnimation(mAnim);
        second.startAnimation(mAnim);
        third.startAnimation(mAnim);
        fourth.startAnimation(mAnim);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.monalisa);
        mZoomImage.setImageBitmap(b);

        //set the image to draw mode, just to test
        mZoomImage.setDrawMode(false);
       // mZoomImage.setOnTouchListener(this);




        return v;
    }
}
