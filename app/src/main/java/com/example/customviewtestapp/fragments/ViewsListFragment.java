package com.example.customviewtestapp.fragments;

import com.example.customviewtestapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewsListFragment extends ListFragment {

	private ListView mViewsList;
	private ArrayAdapter<String> mAdapter;
	private String[] mNames = new String[] { "Color Picker View", "Animated Bubble View", "GriddedImageView" };
	private onItemSelectedListener mListener;


    //TESTING GIT IN ANDROID STUDIO//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);

		// mViewsList = (ListView) v.findViewById(R.id.list);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mNames);
		setListAdapter(mAdapter);
		// getListView().setOnItemClickListener(this);

		// mViewsList.setAdapter(mAdapter);

		return v;
	}



	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		try {
			mListener = (onItemSelectedListener) activity;
		} catch (ClassCastException e) {

			e.printStackTrace();
			Log.e("ViewsListFragment",
					"ClassCastException, " + activity.getTitle()
							+ " must implement onItemSelectedListener");
		}
	}

	public interface onItemSelectedListener {

		public abstract void onItemSelected(int position);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		// .show();

		mListener.onItemSelected(position);

	}
}
