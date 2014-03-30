package com.example.customviewtestapp.views;

import com.example.customviewtestapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorPickerView extends LinearLayout {

	private View mValue;
	private ImageView mImage;

	public ColorPickerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Obtain attributes
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ColorPickerOptions, 0, 0);

		String titleText = a
				.getString(R.styleable.ColorPickerOptions_titleText);
		int valueColor = a.getColor(R.styleable.ColorPickerOptions_valueColor,
				android.R.color.holo_blue_light);
		a.recycle();

		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.color_options_view, this, true);

		TextView title = (TextView) getChildAt(0);
		title.setText(titleText);

		mValue = getChildAt(1);
		mValue.setBackgroundColor(valueColor);

		mImage = (ImageView) getChildAt(2);
	}

	public ColorPickerView(Context context) {
		this(context, null);
	}

	public void setValueColor(int color) {
		mValue.setBackgroundColor(color);
	}

	public void setImageVisible(boolean visible) {
		mImage.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

}
