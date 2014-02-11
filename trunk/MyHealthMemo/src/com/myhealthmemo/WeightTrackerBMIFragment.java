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
	
	private TextView mTextView, mTextView2;
	private ImageView mImageView;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private String gender,bmi,weight_status;
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
	    mTextView2 = (TextView) rootView.findViewById(R.id.textView2);
	    gender = mPrefs.getString("gender", "");
		bmi = mPrefs.getString("bmi", "");
		weight_status = mPrefs.getString("weight_status", "");
		age = calculateAge(mPrefs.getString("dob",""));
		mTextView.setText(bmi);
	    displayGraph();
		return rootView;
	}
	
	public void displayGraph(){
		if(gender.equals("Male")){
			if(weight_status.equals("Severely Underweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
				showText(0);
			} else if(weight_status.equals("Underweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
				showText(1);
			} else if(weight_status.equals("Acceptable Weight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
				showText(2);
			} else if(weight_status.equals("Overweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
				showText(3);
			} else if(weight_status.equals("Severely Overweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
				showText(4);
			}
		} else if(gender.equals("Female")){
			if(weight_status.equals("Severely Underweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				showText(0);
			} else if(weight_status.equals("Underweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				showText(1);
			} else if(weight_status.equals("Acceptable Weight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				showText(2);
			} else if(weight_status.equals("Overweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				showText(3);
			} else if(weight_status.equals("Severely Overweight")){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				showText(4);
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
	
	public void showText(int a){
		switch(a){
		case 0:
			mTextView2.setText("You are in Severely Underweight range. " +
			 		"1. Encourage him/her by providing small frequent meals if he/she has small appetite. " +
			 		"2. Giving healthy snacks between meals to increase calories intake. " +
			 		"3. Increasing food portion size gradually");
			break;
		case 1:
			mTextView2.setText("You are in Underweight range. 1. Encourage him/her by providing small frequent meals if " +
			 		"he/she has small appetite. 2. Giving healthy snacks between meals to increase calories intake. 3. " +
			 		"Increasing food portion size gradually");
			break;
		case 2:
			mTextView2.setText("You are in acceptable weight range. " +
			 		"1. Engage in regular physical activity" +
			 		"2. Encourage him/ her to stay active during recess or after school through games and fun play. " +
			 		"3. Remember 60 minutes of moderate-intensity physical activity on 5 or more days a week " +
			 		"The 60-minutes can be broken up into shorter blocks of 10-15 minutes each time.");
			break;
		case 3:
			mTextView2.setText("You are in Overweight range. Overweight and obesity may increase your child's risk of health problems. " +
			 		"1. Adding more fruit, vegetables and whole-grains to his/ her diet." +
			 		"2. Selecting healthier choices when eating out and limiting fast food." +
			 		"3. Avoid food that is high in fat, salt and sugar. " +
			 		"4. Engaging him/ her in regular physical activity." +
			 		"5. Remember 60 minutes of moderate-intensity physical activity on 5 or more days a week " +
			 		"The 60-minutes can be broken up into shorter blocks of 10-15 minutes each time.");
			break;
		case 4:
			mTextView2.setText("You are in Severely Overweight range. Overweight and obesity may increase your child's risk of health problems. " +
			 		"1. Adding more fruit, vegetables and whole-grains to his/ her diet." +
			 		"2. Selecting healthier choices when eating out and limiting fast food." +
			 		"3. Avoid food that is high in fat, salt and sugar. " +
			 		"4. Engaging him/ her in regular physical activity." +
			 		"5. Remember 60 minutes of moderate-intensity physical activity on 5 or more days a week. " +
			 		"The 60-minutes can be broken up into shorter blocks of 10-15 minutes each time.");
			break;
		}
	}

}
