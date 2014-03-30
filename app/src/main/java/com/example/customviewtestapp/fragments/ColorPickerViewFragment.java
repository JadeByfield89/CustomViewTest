package com.example.customviewtestapp.fragments;

import com.example.customviewtestapp.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ColorPickerViewFragment extends android.support.v4.app.Fragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.color_picker_fragment, container,
				false);

		return v;

	}

}
