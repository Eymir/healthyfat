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
	    mTextView2 = (TextView) rootView.findViewById(R.id.textView2);
	    gender = mPrefs.getString("gender", "");
		bmi = mPrefs.getString("bmi", "");
		age = calculateAge(mPrefs.getString("dob",""));
		mTextView.setText(bmi);
	    displayGraph();
	    
	    
		return rootView;
	}
	public void displayGraph(){
	String Text = mTextView.getText().toString();
	

	double body = Double.parseDouble(bmi);
	if(gender.equals("Male") )
	{
	 if(age == 6 ){
		 if (body < 12.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
			 
		 } else if (body >=12.9 && body < 13.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
			 
		 } else if (body >=13.2 && body < 18.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if (body >= 18.9 && body <21.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
			 
		 }else if (body >=21.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
			
		 }
	 } else if (age == 7){
		 if(body <13.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
			 
		 }else if (body >=13.1 && body <13.3 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
			 
		 }else if (body >=13.3 && body <19.7 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if(body >=19.7 && body < 23.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
			 
		 }else if (body >=23.1 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
			 
		 }
	 } else if (age == 8){
		 if(body <13.3   ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
		 }else if (body >=13.3 && body <13.6  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
		 }else if (body >=13.6 && body <20.9  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
		 }else if(body >=20.9 && body <24.7  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
		 } else if (body >= 24.7){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 9){
		 if(body <13.5){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
			 
		 }else if (body >=13.5 && body <13.9 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
			
		 }else if (body >=13.9 && body <21.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if(body >=21.9 && body <26.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);

		 }else if (body >=26.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
			 
		 }
	 } else if (age == 10){
		 if(body <13.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
			 
		 }else if (body >=13.9 &&  body <14.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
			 
		 }else if (body >=14.4 && body < 22.8 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if(body >=22.8 && body < 27.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);

		 }else if (body >=27.4 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 11){
		 if(body <14.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);

		 }else if (body >= 14.2 && body < 14.6){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);

		 }else if (body >=14.6 && body < 23.8){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);

		 }else if(body >=23.8 && body < 28.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);

		 }else if (body >=28.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);

		 }
	 } else if (age == 12){
		 if(body <14.6){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);

		 }else if (body >=14.6 && body <14.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);

		 }else if (body >=14.9 && body <24.5){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
	
		 }else if(body >=24.5 && body <29.3){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
		 
		 }else if (body >=29.3){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 13){
		 if(body <14.8){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);

		 }else if (body >=14.8 && body < 15.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);

		 }else if (body >=15.2 && body < 25.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);

		 }else if(body >=25.1 && body < 30.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);

		 }else if (body >=30.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);

		 }
	}else if (age == 14){
		 if(body <15.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);

		 }else if (body >=15.1 && body <15.5){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);

		 }else if (body >=15.5 && body < 25.6){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if(body >=25.6 && body < 30.7){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
			 
		 }else if (body >=30.7){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 15){
		 if(body <15.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
		 }else if (body >=15.4 && body < 15.9){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
			 
		 }else if (body >=15.9 && body <26.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
			 
		 }else if(body >=26.2 && body < 31.3){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
			 
		 }else if (body >=31.3){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 16){
		 if(body <15.7){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
		 }else if (body >=15.7 && body <16.2 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
		 }else if (body >=16.2 && body <26.6 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
		 }else if(body >=26.6 && body <31.8 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
		 }else if (body >=31.8){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 } else if (age == 17){
		 if(body <16  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(1);
		 }else if (body >=16 && body < 16.4){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(2);
		 }else if (body >=16.4 && body < 27.1){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(3);
		 }else if(body >= 27 && body < 32.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(4);
		 }else if (body >=32.2){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(5);
		 }
	 } else if (age == 18){
		 if(body <16.2  ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyunderweight));
			 showText(0);
		 }else if (body >=16.2 && body < 16.6 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_underweight));
			 showText(1);
		 }else if (body >=16.6 && body < 27.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_acceptableweight));
			 showText(2);
		 }else if(body >=27.5 && body <32.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_overweight));
			 showText(3);
		 }else if (body >=32.5 ){
			 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy_severelyoverweight));
			 showText(4);
		 }
	 
        
	} else if (gender.equals("Female")){
		if(age == 6 ){
			if (body <12.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=12.7 && body < 13){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >=13 && body< 18.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 18.7 && body < 20.9  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body >=20.9 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 7){
			if (body <12.8){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=12.8 && body< 13.5 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >=13.5 && body< 19.2 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 19.2 && body< 22   ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>=22  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
				 			
			 }
		} else if (age == 8){
			if (body <13.2){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=13.2 && body<13.6 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 13.6 && body< 20.2  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 20.2 && body< 23.2    ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>=23.2  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 9){
			if (body <13.6){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				showText(0);
			 } else if (body >=13.6 && body<13.9 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 13.9 && body< 21.1  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 21.1 && body< 24.7    ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 24.7 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 10){
			if (body <13.8){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=13.8 && body<14.2 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 14.2 && body< 22  ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 22 && body< 26.8 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 26.8 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 11){
			if (body <14){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=14 && body< 14.5){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 14.5 && body< 22.8 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 22.8 && body< 26.8 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 26.8 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				showText(4);
			 }
		} else if (age == 12){
			if (body <14.3){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=14.3 && body <15){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 15 && body< 23.7 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 23.7 && body< 27.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 27.7 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				showText(4);
			 }
		} else if (age == 13){
			if (body <15){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				showText(0);
			 } else if (body >=15 && body <15.3){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 15.3 && body< 24){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				showText(2);
			 }else if (body > 24.7 && body< 28.3){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				showText(3);
			 }else if (body>= 28.3 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				showText(4);
			 }
		} else if (age == 14){
			if (body <15.2){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				showText(0);
			 } else if (body >=15.2 && body< 15.8){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 15.8 && body< 24.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body > 24.7 && body< 29){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 29){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 15){
			if (body <15.3){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=15.3 && body< 15.9){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 15.9 && body< 25){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 25 && body< 29.5){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 29.5){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 16){
			if (body <15.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=15.7 && body< 16.2){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 16.2 && body< 25.7){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 25.7 && body< 30.1 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 30.1){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 17){
			if (body <16){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=16 && body< 16.3){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 16.3 && body< 25.8){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 25.8 && body< 30 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 30){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		} else if (age == 18){
			if (body <16.1){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyunderweight));
				 showText(0);
			 } else if (body >=16.1 && body< 16.8){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_underweight));
				 showText(1);
			 } else if (body >= 16.8 && body< 26){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_acceptableweight));
				 showText(2);
			 }else if (body >= 26 && body< 30.2 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_overweight));
				 showText(3);
			 }else if (body>= 30.2 ){
				 mImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl_severelyoverweight));
				 showText(4);
			 }
		}
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
