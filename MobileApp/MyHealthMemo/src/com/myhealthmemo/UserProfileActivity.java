package com.myhealthmemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	private SharedPreferences mPrefs;
	private TextView name, dob, gender, height, weight, bmi, education, school, classes, reg_no, activity_level;
	private ImageView pp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		PreferenceManager.setDefaultValues(UserProfileActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		resumeInit();
		
	}
	
	public void convertImage(){
		String path = mPrefs.getString("profilePic", "");
		byte [] encodeByte=Base64.decode(path,Base64.DEFAULT);
		Bitmap myBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		pp.setImageBitmap(myBitmap);
	}
	
	public void init(){
		name = (TextView) findViewById(R.id.name_value);
		pp = (ImageView) findViewById(R.id.pp_value);
		dob = (TextView) findViewById(R.id.dob_value);
		gender = (TextView) findViewById(R.id.gender_value);
		height = (TextView) findViewById(R.id.height_value);
		weight = (TextView) findViewById(R.id.weight_value);
		bmi = (TextView) findViewById(R.id.bmi_value);
		education = (TextView) findViewById(R.id.education_value);
		school = (TextView) findViewById(R.id.school_value);
		classes = (TextView) findViewById(R.id.class_value);
		reg_no = (TextView) findViewById(R.id.reg_no_value);
		activity_level = (TextView) findViewById(R.id.activity_level_value);
	}
	
	public void resumeInit(){
		name.setText(mPrefs.getString("userName", ""));
		convertImage();
		dob.setText(mPrefs.getString("dob", ""));
		gender.setText(mPrefs.getString("gender", ""));
		height.setText(mPrefs.getString("height", ""));
		weight.setText(mPrefs.getString("weight", ""));
		bmi.setText(mPrefs.getString("bmi", ""));
		education.setText(mPrefs.getString("education", ""));
		school.setText(mPrefs.getString("school", ""));
		classes.setText(mPrefs.getString("class", ""));
		reg_no.setText(mPrefs.getString("reg_no", ""));
		activity_level.setText(mPrefs.getString("activity_level", ""));
	}

}
