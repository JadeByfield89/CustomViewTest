package com.example.customviewtestapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customviewtestapp.R;

/**
 * Created by Jade Byfield on 3/27/2014.
 */
public class BubbleViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bubble_view_fragment, container, false);
        return v;
    }
}
