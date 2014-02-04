package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private String gender,bmi;
	private int age;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_bmi_fragment, container, false);
		PreferenceManager.setDefaultValues(getActivity(), R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mPrefsEdit = mPrefs.edit();
		mImageView = (ImageView) rootView.findViewById(R.id.imageViewGraph);
	    mTextView = (TextView) rootView.findViewById(R.id.textViewBMI);
	    gender = mPrefs.getString("gender", "");
		bmi = mPrefs.getString("bmi", "");
		age = calculateAge(mPrefs.getString("age",""));
		mTextView.setText(bmi);
	    displayGraph();
	    
	    
		return rootView;
	}
	public void displayGraph(){
	String Text = mTextView.getText().toString();
	int Num = Integer.parseInt(Text);

	double body = Double.parseDouble(bmi);
	if(gender.equals("Male") )
	{
	 if(age == 6 ){
		 if (body < 12.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
		 } else if (body >=12.9 && body < 13.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
		 } else if (body >=13.2 && body < 18.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
		 }else if (body >= 18.9 && body <21.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
		 }else if (body >=21.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
		 }
	 } else if (age == 7){
		 if(body <13.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
		 }else if (body >=13.1 && body <13.3 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
		 }else if (body >=13.3 && body <19.7 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
		 }else if(body >=19.7 && body < 23.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
		 }else if (body >=23.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
		 }
	 } else if (age == 8){
		 if(body <13.3   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
		 }else if (body >=13.3 && body <13.6  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
		 }else if (body >=13.6 && body <20.9  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
		 }else if(body >=20.9 && body <24.7  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
		 }else if (body >=24.7  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
		 }
	 } else if (age == 9){
		 if(body <13.5   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
		 }else if (body >=13.5 && body <13.9   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
		 }else if (body >=13.9 && body <21.9   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
		 }else if(body >=21.9 && body <26.1   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
		 }else if (body >=26.1  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
		 }
	 } else if (age == 10){
		 
	 } else if (age == 11){
		 
	 } else if (age == 12){
		 
	 } else if (age == 13){
		 
	 } else if (age == 14){
		 
	 } else if (age == 15){
		 
	 } else if (age == 16){
		 
	 } else if (age == 17){
		 
	 } else if (age == 18){
		 
	 }
        
	} else if (gender.equals("Female")){
		if(age == 6 ){
			 
		} else if (age == 7){
			 
		} else if (age == 8){
			 
		} else if (age == 9){
			 
		} else if (age == 10){
			 
		} else if (age == 11){
			 
		} else if (age == 12){
			 
		} else if (age == 13){
			 
		} else if (age == 14){
			 
		} else if (age == 15){
			 
		} else if (age == 16){
		 
		} else if (age == 17){
			 
		} else if (age == 18){
			 
		}
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
	
	public int calculateAge(String dob){
		Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
	    Date d = null;
	    try {
	    	d = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
	    		.parse(mPrefs.getString("dob", ""));
	        
	    } catch (ParseException e) {
	    	
	    }
	    cal2.setTime(d);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH) + 1;
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int current_age = year - year2;
		if (month2 > month){
			current_age = current_age-1;
		}
		else if (month2 == month){
			if (day < day2){
				current_age = current_age-1;
			}
		}
		return current_age;
	}

}
