package com.myhealthmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeightTrackerBMIFragment extends Fragment {
	
	private TextView mTextView;
	private TextView mTextView2;
	private ImageView mImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_bmi_fragment, container, false);
		mImageView = (ImageView) rootView.findViewById(R.id.imageViewGraph);
	    mTextView = (TextView) rootView.findViewById(R.id.textViewBMI);
	    mTextView2 = (TextView) rootView.findViewById(R.id.textViewGender);
	    displayGraph();
		return rootView;
	}
	public void displayGraph(){
	String Text = mTextView.getText().toString();
	String Gen = mTextView2.getText().toString();
	int Num = Integer.parseInt(Text);
	
	if(Num == 25 &&  Gen == "Female" )
	{
        mImageView.setImageResource(R.drawable.girl_acceptableweight);
	}
	
}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight, menu);
	}
	
	public static WeightTrackerBMIFragment newInstance(String text) {

		WeightTrackerBMIFragment f = new WeightTrackerBMIFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
