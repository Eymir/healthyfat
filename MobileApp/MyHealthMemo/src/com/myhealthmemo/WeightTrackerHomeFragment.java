package com.myhealthmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeightTrackerHomeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_tracker_home_fragment, container, false);
		TextView tv = (TextView) rootView.findViewById(R.id.tvFragFirst);
		tv.setText(getArguments().getString("msg"));
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight, menu);
	}
	
	public static WeightTrackerHomeFragment newInstance(String text) {

		WeightTrackerHomeFragment f = new WeightTrackerHomeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
