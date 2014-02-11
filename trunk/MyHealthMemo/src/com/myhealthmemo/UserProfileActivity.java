package com.myhealthmemo;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements OnClickListener, 
	NumberPicker.OnValueChangeListener, OnItemSelectedListener {

	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private TextView name, dob, gender, height, weight, bmi, education, school, classes, reg_no, activity_level;
	private ImageView pp;
	private RelativeLayout pp_c,height_c, weight_c, education_c, school_c, classes_c, reg_no_c, activity_level_c;
	private Button mBtn;
	private RadioButton mRB,mRB2;
	private String w,path;
	private String[] mPriSch, mSecSch, nums;
	private double daily_calories_need,bmr,wp,hp,ap;
	private ImageButton mBrowse,mTakePhoto,mRemove;
	private Intent mIntent;
	private static final int mReq_ID = 0;
	private static final int mCamReq_ID = 1;
	private Drawable profile_pic;
	private Bitmap photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		PreferenceManager.setDefaultValues(UserProfileActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		// Show the Up button in the action bar.
		setupActionBar();
		init();
		setClickListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		resumeInit();
		
	}
	
	/*A method to decode the Base64 String
	 * and then decode the results, which is a Byte[] into bitmap
	 * then set the ImageView to display the bitmap
	 */
	private void convertImage(){
		path = mPrefs.getString("profilePic", "");
		byte [] encodeByte=Base64.decode(path,Base64.DEFAULT);
		Bitmap myBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		pp.setImageBitmap(myBitmap);
	}
	
	//A method to initialize all layout to corresponding variable
	private void init(){
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
		pp_c = (RelativeLayout) findViewById(R.id.pp_container);
		height_c = (RelativeLayout) findViewById(R.id.height_container);
		weight_c = (RelativeLayout) findViewById(R.id.weight_container);
		education_c = (RelativeLayout) findViewById(R.id.education_container);
		school_c = (RelativeLayout) findViewById(R.id.school_container);
		classes_c = (RelativeLayout) findViewById(R.id.class_container);
		reg_no_c = (RelativeLayout) findViewById(R.id.reg_no_container);
		activity_level_c = (RelativeLayout) findViewById(R.id.activity_level_container);
	}
	
	
	//A method to reset all text fields
	private void resumeInit(){
		name.setText(mPrefs.getString("userName", ""));
		convertImage();
		dob.setText(mPrefs.getString("dob", ""));
		gender.setText(mPrefs.getString("gender", ""));
		height.setText(mPrefs.getString("height", "") + " Kg");
		weight.setText(mPrefs.getString("weight", "") + " Cm");
		bmi.setText(mPrefs.getString("bmi", ""));
		education.setText(mPrefs.getString("education", ""));
		school.setText(mPrefs.getString("school", ""));
		classes.setText(mPrefs.getString("class", ""));
		reg_no.setText(mPrefs.getString("reg_no", ""));
		activity_level.setText(mPrefs.getString("activity_level", ""));
	}
	
	//A method to assign listener to corresponding layout variable
	
	private void setClickListener(){
		pp_c.setOnClickListener(this);
		height_c.setOnClickListener(this);
		weight_c.setOnClickListener(this);
		education_c.setOnClickListener(this);
		school_c.setOnClickListener(this);
		classes_c.setOnClickListener(this);
		reg_no_c.setOnClickListener(this);
		activity_level_c.setOnClickListener(this);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.pp_container:
			showImageDialog();
			break;
		case R.id.height_container:
			showHeightDialog();
			break;
		case R.id.weight_container:
			showWeightDialog();
			break;
		case R.id.education_container:
			showEducationDialog();
			break;
		case R.id.school_container:
			showSchoolDialog();
			break;
		case R.id.class_container:
			showClassDialog();
			break;
		case R.id.reg_no_container:
			showRegNoDialog();
			break;
		case R.id.activity_level_container:
			showActivityLevelDialog();
			break;
		default:
			break;
		}
	}
	
	//Display Image Dialog
	private void showImageDialog() {
		/*Display a dialog which includes 4 buttons:
		 * browse button
		 * take photo button
		 * remove button
		 * done button
		 */
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.add_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		mBrowse =  (ImageButton) mDialog.findViewById(R.id.browse);
		mTakePhoto = (ImageButton) mDialog.findViewById(R.id.take_photo);
		mRemove = (ImageButton) mDialog.findViewById(R.id.remove);
		
		profile_pic = getResources().getDrawable(R.drawable.default_profile_pic);
		Bitmap ak = ((BitmapDrawable)profile_pic).getBitmap();
		String apath = convertPF(ak);
		if (apath.equals(mPrefs.getString("profilePic", ""))){
			mRemove.setVisibility(View.GONE);
		}

		//If user clicked on done button, dismiss the Dialog
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		/*If user clicked on browse, define the action and 
		 * startActivityForResult with the key mReg_ID
		 */
		mBrowse.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				mIntent = new Intent();
				mIntent.setAction(Intent.ACTION_GET_CONTENT);
				mIntent.addCategory(Intent.CATEGORY_OPENABLE);
				mIntent.setType("image/*");
				startActivityForResult(mIntent, mReq_ID);
				mDialog.dismiss();
			}			
		});
		/*If user clicked on take photo, define the action and 
		 * startActivityForResult with the key mCamReq_ID
		 */
		mTakePhoto.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){	
				mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(mIntent, mCamReq_ID);
				mDialog.dismiss();
			}
		});
		
		mRemove.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				setImageToDefault();
				photo = ((BitmapDrawable)profile_pic).getBitmap();
				path = convertPF(photo);
				pp.setImageBitmap(photo);
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	//Display Height Dialog
	private void showHeightDialog() {
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Height");
		mDialog.setContentView(R.layout.number_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		int a = Integer.parseInt(mPrefs.getString("height", ""));
		mNumPk.setMaxValue(200);
		mNumPk.setMinValue(100);
		mNumPk.setValue(a);
		mNumPk.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("height", String.valueOf(mNumPk.getValue()));
				String w = mPrefs.getString("weight", "");
				String bmi = calculateBMI(String.valueOf(mNumPk.getValue()),w);
				mPrefsEdit.putString("bmi", bmi);
				mPrefsEdit.putString("weight_status",calculateWeightStatus(bmi));
				mPrefsEdit.putString("daily_calories_need", calculateDCN(String.valueOf(mNumPk.getValue()),w));
				mPrefsEdit.commit();
				mDialog.dismiss();
				onResume();
			}
			
			//A method to calculate BMI
			public String calculateBMI(String height, String weight){
				BigDecimal kg_weight = new BigDecimal(weight);
				BigDecimal cm_height = new BigDecimal(height);
				BigDecimal hundred = new BigDecimal("100");
				BigDecimal m_height = cm_height.divide(hundred,1,RoundingMode.HALF_UP);
				BigDecimal m_height_sq = m_height.multiply(m_height);
				BigDecimal bmi = kg_weight.divide(m_height_sq,1,RoundingMode.HALF_UP);
				return bmi.toString();
			}
			
			//A method to determine the age
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
			
			//A method to calculate daily calories need
			public String calculateDCN(String height, String weight){
				double kg_weight = Double.parseDouble(weight);
				double cm_height = Double.parseDouble(height);
				int current_age = calculateAge(mPrefs.getString("dob", ""));

				if(mPrefs.getString("gender","").equals("Male")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap + 5;
				}
				else if (mPrefs.getString("gender","").equals("Female")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap - 161;
				}
				
				if (mPrefs.getString("activity_level", "").equals("little or no exercise")){
					daily_calories_need = bmr * 1.2;
				}
				else if (mPrefs.getString("activity_level", "").equals("light exercise/sports")){
					daily_calories_need = bmr * 1.375;
				}
				else if (mPrefs.getString("activity_level", "").equals("moderate exercise/sports")){
					daily_calories_need = bmr * 1.55;
				}
				else if (mPrefs.getString("activity_level", "").equals("hard exercise/sports")){
					daily_calories_need = bmr * 1.725;
				}
				else{
					daily_calories_need = bmr * 1.9;
				}
				int int_dcn = (int) Math.round(daily_calories_need);
				return String.valueOf(int_dcn);
			}
			
			public String calculateWeightStatus(String e){
				int age = calculateAge(mPrefs.getString("dob", ""));
				double body = Double.parseDouble(e);
				String weight_status = null;
				if(mPrefs.getString("gender", "").equals("Male") ) {
					if(age == 6 ){
						if (body < 12.8){	
							weight_status = "Severely Underweight";		
						} else if (body >=12.9 && body < 13.1){		
							weight_status = "Underweight";
						} else if (body >=13.2 && body < 18.8){
							weight_status = "Acceptable Weight";
						} else if (body >= 18.9 && body <21.4 ){
							weight_status = "Overweight";
						} else if (body >=21.5 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 7){
						if(body <13.0){
							weight_status = "Severely Underweight";
						} else if (body >=13.1 && body <13.3 ){
							weight_status = "Underweight";
						} else if (body >=13.3 && body <19.8 ){
							weight_status = "Acceptable Weight";
						} else if(body >=19.9 && body < 23.0 ){
							weight_status = "Overweight";
						} else if (body >=23.1 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 8){
						if(body <13.2  ){
							weight_status = "Severely Underweight";
						} else if (body >=13.3 && body <13.6  ){
							weight_status = "Underweight";
						} else if (body >=13.7 && body <20.9  ){
							weight_status = "Acceptable Weight";
						} else if(body >=21 && body <24.6  ){
							weight_status = "Overweight";
						} else if (body >= 24.7){
							weight_status = "Severely Overweight";
						}
					} else if (age == 9){
						if(body <13.5){
							weight_status = "Severely Underweight";
						} else if (body >=13.6 && body <13.8 ){
							weight_status = "Underweight";
						} else if (body >=13.9 && body <21.8){
							weight_status = "Acceptable Weight";
						} else if(body >=21.9 && body <26.0){
							weight_status = "Overweight";
						} else if (body >=26.1){
							weight_status = "Severely Overweight";
						}
					} else if (age == 10){
						if(body <13.8){
							weight_status = "Severely Underweight";
						} else if (body >=13.9 &&  body <14.1){
							weight_status = "Underweight";
						} else if (body >=14.2 && body < 22.7 ){
							weight_status = "Acceptable Weight";
						} else if(body >=22.8 && body < 27.3){
							weight_status = "Overweight";
						} else if (body >=27.4 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 11){
						if(body <14.1){
							weight_status = "Severely Underweight";
						} else if (body >= 14.2 && body < 14.5){
							weight_status = "Underweight";
						} else if (body >=14.6 && body < 23.6){
							weight_status = "Acceptable Weight";
						} else if(body >=23.7 && body < 28.3){
							weight_status = "Overweight";
						} else if (body >=28.4){
							weight_status = "Severely Overweight";
						}
					} else if (age == 12){
						if(body <14.4){
							weight_status = "Severely Underweight";
						} else if (body >=14.5 && body <14.8){
							weight_status = "Underweight";
						} else if (body >=14.9 && body <24.3){
							weight_status = "Acceptable Weight";
						} else if(body >=24.4 && body <29.2){
							weight_status = "Overweight";
						} else if (body >=29.3){
							weight_status = "Severely Overweight";
						}
					} else if (age == 13){
						if(body <14.7){
							weight_status = "Severely Underweight";
						} else if (body >=14.8 && body < 15.1){
							weight_status = "Underweight";
						} else if (body >=15.2 && body < 25.0){
							weight_status = "Acceptable Weight";
						} else if(body >=25.1 && body < 30.0){
							weight_status = "Overweight";
						} else if (body >=30.1){
							weight_status = "Severely Overweight";
						}
					}else if (age == 14){
						if(body <15.0){
							weight_status = "Severely Underweight";
						} else if (body >=15.1 && body <15.4){
							weight_status = "Underweight";
						} else if (body >=15.5 && body < 25.5){
							weight_status = "Acceptable Weight";
						} else if(body >=25.6 && body < 30.6){
							weight_status = "Overweight";
						} else if (body >=30.7){
							weight_status = "Severely Overweight";
						}
					} else if (age == 15){
						if(body <15.3){
							weight_status = "Severely Underweight";
						} else if (body >=15.4 && body < 15.8){
							weight_status = "Underweight";
						} else if (body >=15.9 && body <26.1){
							weight_status = "Acceptable Weight";
						} else if(body >=26.2 && body < 31.2){  
							weight_status = "Overweight";
						} else if (body >=31.3){
							weight_status = "Severely Overweight";	
						}
					} else if (age == 16){
						if(body <15.6){				
							weight_status = "Severely Underweight";
						} else if (body >=15.7 && body <16.1 ){
							weight_status = "Underweight";
						} else if (body >=16.2 && body <26.5 ){
							weight_status = "Acceptable Weight";
						} else if(body >=26.6 && body <31.7 ){
							weight_status = "Overweight";
						} else if (body >=31.8){
							weight_status = "Severely Overweight";
						}
					} else if (age == 17){
						if(body <15.9  ){
							weight_status = "Severely Underweight";
						} else if (body >=16.0 && body < 16.3){
							weight_status = "Underweight";
						} else if (body >=16.4 && body < 27.0){
							weight_status = "Acceptable Weight";
						} else if(body >= 27.1 && body < 32.1){
							weight_status = "Overweight";
						} else if (body >=32.2){
							weight_status = "Severely Overweight";
						}
					} else if (age == 18){
						if(body <16.1  ){
							weight_status = "Severely Underweight";
						} else if (body >=16.2 && body < 16.6 ){
							weight_status = "Underweight";
						} else if (body >=16.7 && body < 27.4 ){
							weight_status = "Acceptable Weight";
						} else if(body >=27.5 && body <32.4 ){
							weight_status = "Overweight";
						} else if (body >=32.5 ){
							weight_status = "Severely Overweight";
						}
					}
				} else if (mPrefs.getString("gender", "").equals("Female")){
					if(age == 6 ){	
						if (body <12.6){	
							weight_status = "Severely Underweight";		
						} else if (body >=12.7 && body < 12.8){
							weight_status = "Underweight";
						} else if (body >=12.9 && body< 18.3){	 
							weight_status = "Acceptable Weight";
						} else if (body >= 18.4 && body < 20.5 ){
							weight_status = "Overweight";
						} else if (body >=20.6 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 7){
						if (body <12.8){
							weight_status = "Severely Underweight";
						} else if (body >=12.9 && body< 13.1 ){
							weight_status = "Underweight";
						} else if (body >=13.2 && body< 19.1 ){		 	 
							weight_status = "Acceptable Weight";
						} else if (body >= 19.2 && body< 21.8   ){
							weight_status = "Overweight";
						} else if (body>=21.9){
							weight_status = "Severely Overweight";
						}
					} else if (age == 8){
						if (body <13.1){
							weight_status = "Severely Underweight";
						} else if (body >=13.2 && body<13.4){
							weight_status = "Underweight";
						} else if (body >= 13.5 && body< 20.1  ){
							weight_status = "Acceptable Weight";
						} else if (body >= 20.2 && body< 23.1  ){
							weight_status = "Overweight";
						} else if (body>=23.2  ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 9){
						if (body <13.4){
							weight_status = "Severely Underweight";
						} else if (body >=13.5 && body<13.7 ){
							weight_status = "Underweight";
						} else if (body >= 13.8 && body< 21.0 ){
							weight_status = "Acceptable Weight";
						} else if (body >= 21.1 && body< 24.4    ){
							weight_status = "Overweight";
						} else if (body>= 24.5 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 10){
						if (body <13.7){
							weight_status = "Severely Underweight";
						} else if (body >=13.8 && body<14.1 ){
							weight_status = "Underweight";
						} else if (body >= 14.2 && body< 21.9  ){ 
							weight_status = "Acceptable Weight";
						} else if (body >= 22 && body< 25.6 ){
							weight_status = "Overweight";	
						} else if (body>= 26.7 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 11){	
						if (body <14.1){
							weight_status = "Severely Underweight";
						} else if (body >=14.2 && body< 14.4){
							weight_status = "Underweight";
						} else if (body >= 14.5 && body< 22.7 ){
							weight_status = "Acceptable Weight";
						} else if (body >= 22.8 && body< 26.6){
							weight_status = "Overweight";
						} else if (body>= 26.7 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 12){
						if (body <14.4){
							weight_status = "Severely Underweight";
						 } else if (body >=14.5 && body <14.8){
							 weight_status = "Underweight";
						 } else if (body >= 14.9 && body< 23.4 ){
							 weight_status = "Acceptable Weight";
						 } else if (body >= 23.5 && body< 27.5){
							 weight_status = "Overweight";
						 } else if (body>= 27.6 ){
							 weight_status = "Severely Overweight";
						 }
					} else if (age == 13){
						if (body <14.8){
							weight_status = "Severely Underweight";
						} else if (body >=14.9 && body <15.2){
							weight_status = "Underweight";
						} else if (body >= 15.3 && body< 24.0){
							weight_status = "Acceptable Weight";
						} else if (body > 24.1 && body< 28.3){
							weight_status = "Overweight";
						} else if (body>= 28.4 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 14){
						if (body <15.1){
							weight_status = "Severely Underweight";
						} else if (body >=15.2 && body< 15.5){
							weight_status = "Underweight";
						} else if (body >= 15.6 && body< 24.6){ 
							weight_status = "Acceptable Weight";
						} else if (body > 24.7 && body< 28.9){
							weight_status = "Overweight";
						} else if (body>= 29){
							weight_status = "Severely Overweight";
						}
					} else if (age == 15){
						if (body <15.4){
							weight_status = "Severely Underweight";
						} else if (body >=15.5 && body< 15.8){
							weight_status = "Underweight";
						} else if (body >= 15.9 && body< 25.0){
							weight_status = "Acceptable Weight";
						} else if (body >= 25.1 && body< 29.4){
							weight_status = "Overweight";
						} else if (body>= 29.5){
							weight_status = "Severely Overweight";
						}
					} else if (age == 16){
						if (body <15.7){
							weight_status = "Severely Underweight";
						} else if (body >=15.8 && body< 16.1){
							weight_status = "Underweight";
						} else if (body >= 16.2 && body< 25.4){
							weight_status = "Acceptable Weight";
						} else if (body >= 25.5 && body< 29.7 ){
							weight_status = "Overweight";
						} else if (body>= 29.8){
							weight_status = "Severely Overweight";
						}
					} else if (age == 17){
						if (body <15.9){
							weight_status = "Severely Underweight";
						} else if (body >=16.0 && body< 16.3){
							weight_status = "Underweight";
						} else if (body >= 16.4 && body< 25.9){
							weight_status = "Acceptable Weight";
						} else if (body >= 26.0 && body< 30.0 ){
							weight_status = "Overweight";
						} else if (body>= 31.0){
							weight_status = "Severely Overweight";
						}
					} else if (age == 18){
						if (body <16.1){
							weight_status = "Severely Underweight";
						} else if (body >=16.2 && body< 16.5){
							weight_status = "Underweight";
						} else if (body >= 16.6 && body< 25.9){
							weight_status = "Acceptable Weight";
						} else if (body >= 26.0 && body< 30.3 ){
							weight_status = "Overweight";
						} else if (body>= 30.4 ){
							weight_status = "Severely Overweight";
						}
					}	
				}
				return weight_status;
			}
		});
		mDialog.show();
	}
	
	
	//Display Weight Dialog
	private void showWeightDialog() {
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Weight");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		w = mPrefs.getString("weight", "");
		nums =  w.split("\\.");
		int whole = Integer.parseInt(nums[0]);
		int decimal = Integer.parseInt(nums[1]);
		mNumPk.setMaxValue(100);
		mNumPk.setMinValue(0);
		mNumPk.setValue(whole);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
		mNumPk2.setValue(decimal);
		mNumPk.setOnValueChangedListener(this);
		mNumPk2.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("weight", String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue()));
				String h = mPrefs.getString("height", "");
				String bmi = calculateBMI(h, String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue()));
				mPrefsEdit.putString("bmi", bmi);
				mPrefsEdit.putString("weight_status", calculateWeightStatus(bmi));
				mPrefsEdit.putString("daily_calories_need", calculateDCN(h, String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue())));
				mPrefsEdit.commit();
				mDialog.dismiss();
				onResume();
			}
			
			//A method to calculate BMI
			public String calculateBMI(String height, String weight){
				BigDecimal kg_weight = new BigDecimal(weight);
				BigDecimal cm_height = new BigDecimal(height);
				BigDecimal hundred = new BigDecimal("100");
				BigDecimal m_height = cm_height.divide(hundred,1,RoundingMode.HALF_UP);
				BigDecimal m_height_sq = m_height.multiply(m_height);
				BigDecimal bmi = kg_weight.divide(m_height_sq,1,RoundingMode.HALF_UP);
				return bmi.toString();
			}
			
			//A method to determine the age
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
			
			//A method to calculate daily calories need
			public String calculateDCN(String height, String weight){
				double kg_weight = Double.parseDouble(weight);
				double cm_height = Double.parseDouble(height);
				int current_age = calculateAge(mPrefs.getString("dob", ""));

				if(mPrefs.getString("gender","").equals("Male")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap + 5;
				}
				else if (mPrefs.getString("gender","").equals("Female")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap - 161;
				}
				
				if (mPrefs.getString("activity_level", "").equals("little or no exercise")){
					daily_calories_need = bmr * 1.2;
				}
				else if (mPrefs.getString("activity_level", "").equals("light exercise/sports")){
					daily_calories_need = bmr * 1.375;
				}
				else if (mPrefs.getString("activity_level", "").equals("moderate exercise/sports")){
					daily_calories_need = bmr * 1.55;
				}
				else if (mPrefs.getString("activity_level", "").equals("hard exercise/sports")){
					daily_calories_need = bmr * 1.725;
				}
				else{
					daily_calories_need = bmr * 1.9;
				}
				int int_dcn = (int) Math.round(daily_calories_need);
				return String.valueOf(int_dcn);
			}
			
			public String calculateWeightStatus(String e){
				int age = calculateAge(mPrefs.getString("dob", ""));
				double body = Double.parseDouble(e);
				String weight_status = "";
				if(mPrefs.getString("gender", "").equals("Male") ) {
					if(age == 6 ){
						if (body < 12.8){	
							weight_status = "Severely Underweight";		
						} else if (body >=12.9 && body < 13.1){		
							weight_status = "Underweight";
						} else if (body >=13.2 && body < 18.8){
							weight_status = "Acceptable Weight";
						} else if (body >= 18.9 && body <21.4 ){
							weight_status = "Overweight";
						} else if (body >=21.5 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 7){
						if(body <13.0){
							weight_status = "Severely Underweight";
						} else if (body >=13.1 && body <13.3 ){
							weight_status = "Underweight";
						} else if (body >=13.3 && body <19.8 ){
							weight_status = "Acceptable Weight";
						} else if(body >=19.9 && body < 23.0 ){
							weight_status = "Overweight";
						} else if (body >=23.1 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 8){
						if(body <13.2  ){
							weight_status = "Severely Underweight";
						} else if (body >=13.3 && body <13.6  ){
							weight_status = "Underweight";
						} else if (body >=13.7 && body <20.9  ){
							weight_status = "Acceptable Weight";
						} else if(body >=21 && body <24.6  ){
							weight_status = "Overweight";
						} else if (body >= 24.7){
							weight_status = "Severely Overweight";
						}
					} else if (age == 9){
						if(body <13.5){
							weight_status = "Severely Underweight";
						} else if (body >=13.6 && body <13.8 ){
							weight_status = "Underweight";
						} else if (body >=13.9 && body <21.8){
							weight_status = "Acceptable Weight";
						} else if(body >=21.9 && body <26.0){
							weight_status = "Overweight";
						} else if (body >=26.1){
							weight_status = "Severely Overweight";
						}
					} else if (age == 10){
						if(body <13.8){
							weight_status = "Severely Underweight";
						} else if (body >=13.9 &&  body <14.1){
							weight_status = "Underweight";
						} else if (body >=14.2 && body < 22.7 ){
							weight_status = "Acceptable Weight";
						} else if(body >=22.8 && body < 27.3){
							weight_status = "Overweight";
						} else if (body >=27.4 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 11){
						if(body <14.1){
							weight_status = "Severely Underweight";
						} else if (body >= 14.2 && body < 14.5){
							weight_status = "Underweight";
						} else if (body >=14.6 && body < 23.6){
							weight_status = "Acceptable Weight";
						} else if(body >=23.7 && body < 28.3){
							weight_status = "Overweight";
						} else if (body >=28.4){
							weight_status = "Severely Overweight";
						}
					} else if (age == 12){
						if(body <14.4){
							weight_status = "Severely Underweight";
						} else if (body >=14.5 && body <14.8){
							weight_status = "Underweight";
						} else if (body >=14.9 && body <24.3){
							weight_status = "Acceptable Weight";
						} else if(body >=24.4 && body <29.2){
							weight_status = "Overweight";
						} else if (body >=29.3){
							weight_status = "Severely Overweight";
						}
					} else if (age == 13){
						if(body <14.7){
							weight_status = "Severely Underweight";
						} else if (body >=14.8 && body < 15.1){
							weight_status = "Underweight";
						} else if (body >=15.2 && body < 25.0){
							weight_status = "Acceptable Weight";
						} else if(body >=25.1 && body < 30.0){
							weight_status = "Overweight";
						} else if (body >=30.1){
							weight_status = "Severely Overweight";
						}
					}else if (age == 14){
						if(body <15.0){
							weight_status = "Severely Underweight";
						} else if (body >=15.1 && body <15.4){
							weight_status = "Underweight";
						} else if (body >=15.5 && body < 25.5){
							weight_status = "Acceptable Weight";
						} else if(body >=25.6 && body < 30.6){
							weight_status = "Overweight";
						} else if (body >=30.7){
							weight_status = "Severely Overweight";
						}
					} else if (age == 15){
						if(body <15.3){
							weight_status = "Severely Underweight";
						} else if (body >=15.4 && body < 15.8){
							weight_status = "Underweight";
						} else if (body >=15.9 && body <26.1){
							weight_status = "Acceptable Weight";
						} else if(body >=26.2 && body < 31.2){  
							weight_status = "Overweight";
						} else if (body >=31.3){
							weight_status = "Severely Overweight";	
						}
					} else if (age == 16){
						if(body <15.6){				
							weight_status = "Severely Underweight";
						} else if (body >=15.7 && body <16.1 ){
							weight_status = "Underweight";
						} else if (body >=16.2 && body <26.5 ){
							weight_status = "Acceptable Weight";
						} else if(body >=26.6 && body <31.7 ){
							weight_status = "Overweight";
						} else if (body >=31.8){
							weight_status = "Severely Overweight";
						}
					} else if (age == 17){
						if(body <15.9  ){
							weight_status = "Severely Underweight";
						} else if (body >=16.0 && body < 16.3){
							weight_status = "Underweight";
						} else if (body >=16.4 && body < 27.0){
							weight_status = "Acceptable Weight";
						} else if(body >= 27.1 && body < 32.1){
							weight_status = "Overweight";
						} else if (body >=32.2){
							weight_status = "Severely Overweight";
						}
					} else if (age == 18){
						if(body <16.1  ){
							weight_status = "Severely Underweight";
						} else if (body >=16.2 && body < 16.6 ){
							weight_status = "Underweight";
						} else if (body >=16.7 && body < 27.4 ){
							weight_status = "Acceptable Weight";
						} else if(body >=27.5 && body <32.4 ){
							weight_status = "Overweight";
						} else if (body >=32.5 ){
							weight_status = "Severely Overweight";
						}
					}
				} else if (mPrefs.getString("gender", "").equals("Female")){
					if(age == 6 ){	
						if (body <12.6){	
							weight_status = "Severely Underweight";		
						} else if (body >=12.7 && body < 12.8){
							weight_status = "Underweight";
						} else if (body >=12.9 && body< 18.3){	 
							weight_status = "Acceptable Weight";
						} else if (body >= 18.4 && body < 20.5 ){
							weight_status = "Overweight";
						} else if (body >=20.6 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 7){
						if (body <12.8){
							weight_status = "Severely Underweight";
						} else if (body >=12.9 && body< 13.1 ){
							weight_status = "Underweight";
						} else if (body >=13.2 && body< 19.1 ){		 	 
							weight_status = "Acceptable Weight";
						} else if (body >= 19.2 && body< 21.8   ){
							weight_status = "Overweight";
						} else if (body>=21.9){
							weight_status = "Severely Overweight";
						}
					} else if (age == 8){
						if (body <13.1){
							weight_status = "Severely Underweight";
						} else if (body >=13.2 && body<13.4){
							weight_status = "Underweight";
						} else if (body >= 13.5 && body< 20.1  ){
							weight_status = "Acceptable Weight";
						} else if (body >= 20.2 && body< 23.1  ){
							weight_status = "Overweight";
						} else if (body>=23.2  ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 9){
						if (body <13.4){
							weight_status = "Severely Underweight";
						} else if (body >=13.5 && body<13.7 ){
							weight_status = "Underweight";
						} else if (body >= 13.8 && body< 21.0 ){
							weight_status = "Acceptable Weight";
						} else if (body >= 21.1 && body< 24.4    ){
							weight_status = "Overweight";
						} else if (body>= 24.5 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 10){
						if (body <13.7){
							weight_status = "Severely Underweight";
						} else if (body >=13.8 && body<14.1 ){
							weight_status = "Underweight";
						} else if (body >= 14.2 && body< 21.9  ){ 
							weight_status = "Acceptable Weight";
						} else if (body >= 22 && body< 25.6 ){
							weight_status = "Overweight";	
						} else if (body>= 26.7 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 11){	
						if (body <14.1){
							weight_status = "Severely Underweight";
						} else if (body >=14.2 && body< 14.4){
							weight_status = "Underweight";
						} else if (body >= 14.5 && body< 22.7 ){
							weight_status = "Acceptable Weight";
						} else if (body >= 22.8 && body< 26.6){
							weight_status = "Overweight";
						} else if (body>= 26.7 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 12){
						if (body <14.4){
							weight_status = "Severely Underweight";
						 } else if (body >=14.5 && body <14.8){
							 weight_status = "Underweight";
						 } else if (body >= 14.9 && body< 23.4 ){
							 weight_status = "Acceptable Weight";
						 } else if (body >= 23.5 && body< 27.5){
							 weight_status = "Overweight";
						 } else if (body>= 27.6 ){
							 weight_status = "Severely Overweight";
						 }
					} else if (age == 13){
						if (body <14.8){
							weight_status = "Severely Underweight";
						} else if (body >=14.9 && body <15.2){
							weight_status = "Underweight";
						} else if (body >= 15.3 && body< 24.0){
							weight_status = "Acceptable Weight";
						} else if (body > 24.1 && body< 28.3){
							weight_status = "Overweight";
						} else if (body>= 28.4 ){
							weight_status = "Severely Overweight";
						}
					} else if (age == 14){
						if (body <15.1){
							weight_status = "Severely Underweight";
						} else if (body >=15.2 && body< 15.5){
							weight_status = "Underweight";
						} else if (body >= 15.6 && body< 24.6){ 
							weight_status = "Acceptable Weight";
						} else if (body > 24.7 && body< 28.9){
							weight_status = "Overweight";
						} else if (body>= 29){
							weight_status = "Severely Overweight";
						}
					} else if (age == 15){
						if (body <15.4){
							weight_status = "Severely Underweight";
						} else if (body >=15.5 && body< 15.8){
							weight_status = "Underweight";
						} else if (body >= 15.9 && body< 25.0){
							weight_status = "Acceptable Weight";
						} else if (body >= 25.1 && body< 29.4){
							weight_status = "Overweight";
						} else if (body>= 29.5){
							weight_status = "Severely Overweight";
						}
					} else if (age == 16){
						if (body <15.7){
							weight_status = "Severely Underweight";
						} else if (body >=15.8 && body< 16.1){
							weight_status = "Underweight";
						} else if (body >= 16.2 && body< 25.4){
							weight_status = "Acceptable Weight";
						} else if (body >= 25.5 && body< 29.7 ){
							weight_status = "Overweight";
						} else if (body>= 29.8){
							weight_status = "Severely Overweight";
						}
					} else if (age == 17){
						if (body <15.9){
							weight_status = "Severely Underweight";
						} else if (body >=16.0 && body< 16.3){
							weight_status = "Underweight";
						} else if (body >= 16.4 && body< 25.9){
							weight_status = "Acceptable Weight";
						} else if (body >= 26.0 && body< 30.0 ){
							weight_status = "Overweight";
						} else if (body>= 31.0){
							weight_status = "Severely Overweight";
						}
					} else if (age == 18){
						if (body <16.1){
							weight_status = "Severely Underweight";
						} else if (body >=16.2 && body< 16.5){
							weight_status = "Underweight";
						} else if (body >= 16.6 && body< 25.9){
							weight_status = "Acceptable Weight";
						} else if (body >= 26.0 && body< 30.3 ){
							weight_status = "Overweight";
						} else if (body>= 30.4 ){
							weight_status = "Severely Overweight";
						}
					}	
				}
				return weight_status;
			}
		});
		mDialog.show();
	}
	
	//Display Education Dialog
	private void showEducationDialog() {
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Education Level");
		mDialog.setContentView(R.layout.education_radio_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final RadioGroup mRG = (RadioGroup) mDialog.findViewById(R.id.radio_Education);
		String c = mPrefs.getString("education", "");
		mRB = (RadioButton) mDialog.findViewById(R.id.radio_primary);
		mRB2 = (RadioButton) mDialog.findViewById(R.id.radio_secondary);
		if(c.equals("Primary")){
			mRB.setChecked(true);
			mRB2.setChecked(false);
		}
		else{
			mRB.setChecked(false);
			mRB2.setChecked(true);
		}
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				switch (mRG.getCheckedRadioButtonId()) {
				case R.id.radio_primary:
					mPrefsEdit.putString("education", mRB.getText().toString()).commit();
					
					break;
				case R.id.radio_secondary:
					mPrefsEdit.putString("education", mRB2.getText().toString()).commit();
					break;
				default:
					break;
				} 
				mDialog.dismiss();
				onResume();
			}
			
		});
		mDialog.show();
	}
	
	//Display School Dialog
	private void showSchoolDialog(){
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Current School");
		mDialog.setContentView(R.layout.school_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		String education = mPrefs.getString("education", "");
		String school = mPrefs.getString("school", "");
		final AutoCompleteTextView mAuto = (AutoCompleteTextView) mDialog.findViewById(R.id.school_Edit);
		mAuto.setText(school);
		//mAuto.setText(school);
		mPriSch = getResources()
				.getStringArray(R.array.primary_school_arrays);
		mSecSch = getResources()
				.getStringArray(R.array.secondary_school_arrays);
		if(education.equals("Primary")){
			mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mPriSch));
		}
		else{
			mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mSecSch));
		}
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("school", mAuto.getText().toString()).commit();
				mDialog.dismiss();
				onResume();
			}
		});
		mDialog.show();
	}
	
	//Display Class Dialog
	private void showClassDialog(){
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Current Class");
		mDialog.setContentView(R.layout.class_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		String c_class = mPrefs.getString("class", "");
		final EditText etx = (EditText) mDialog.findViewById(R.id.class_Edit);
		etx.setText(c_class);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("class", etx.getText().toString()).commit();
				mDialog.dismiss();
				onResume();
			}
		});
		mDialog.show();
	}
	
	
	//Display Register Number Dialog
	private void showRegNoDialog(){
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Register Number");
		mDialog.setContentView(R.layout.reg_no_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		String c_reg_no = mPrefs.getString("reg_no","");
		final EditText etx = (EditText) mDialog.findViewById(R.id.reg_no_Edit);
		etx.setText(c_reg_no);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("reg_no", etx.getText().toString()).commit();
				mDialog.dismiss();
				onResume();
			}
		});
		mDialog.show();
	}
	
	//Display Activity Level Dialog
	private void showActivityLevelDialog(){
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Activity Level");
		mDialog.setContentView(R.layout.activity_level_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		String c_activity_level = mPrefs.getString("activity_level","");
		final Spinner spn = (Spinner) mDialog.findViewById(R.id.activity_level_Edit);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_level_arrays, R.layout.dropdown);
		adapter.setDropDownViewResource(R.layout.dropdown);
		spn.setAdapter(adapter);
		if (c_activity_level.equals("little or no exercise")){
			spn.setSelection(0);
		}
		else if (c_activity_level.equals("light exercise/sports")){
			spn.setSelection(1);
		}
		else if (c_activity_level.equals("moderate exercise/sports")){
			spn.setSelection(2);
		}
		else if (c_activity_level.equals("hard exercise/sports")){
			spn.setSelection(3);
		}
		else if (c_activity_level.equals("very hard exercise/sports")){
			spn.setSelection(4);
		}
		spn.setOnItemSelectedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("activity_level", spn.getSelectedItem().toString());
				String h = mPrefs.getString("height", "");
				String w = mPrefs.getString("weight", "");
				mPrefsEdit.putString("daily_calories_need", calculateDCN(h,w));
				mPrefsEdit.commit();
				mDialog.dismiss();
				onResume();
			}
			
			//A method to determine the age
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
			
			//A method to calculate daily calories need
			public String calculateDCN(String height, String weight){
				double kg_weight = Double.parseDouble(weight);
				double cm_height = Double.parseDouble(height);
				int current_age = calculateAge(mPrefs.getString("dob", ""));

				if(mPrefs.getString("gender","").equals("Male")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap + 5;
				}
				else if (mPrefs.getString("gender","").equals("Female")){
					wp = 10 * kg_weight;
					hp = 6.25 * cm_height;
					ap = 5 * current_age;
					bmr = wp + hp - ap - 161;
				}
				
				if (mPrefs.getString("activity_level", "").equals("little or no exercise")){
					daily_calories_need = bmr * 1.2;
				}
				else if (mPrefs.getString("activity_level", "").equals("light exercise/sports")){
					daily_calories_need = bmr * 1.375;
				}
				else if (mPrefs.getString("activity_level", "").equals("moderate exercise/sports")){
					daily_calories_need = bmr * 1.55;
				}
				else if (mPrefs.getString("activity_level", "").equals("hard exercise/sports")){
					daily_calories_need = bmr * 1.725;
				}
				else{
					daily_calories_need = bmr * 1.9;
				}
				int int_dcn = (int) Math.round(daily_calories_need);
				
				return String.valueOf(int_dcn);
			}
		});
		mDialog.show();
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		parent.getItemAtPosition(pos);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		Log.i("value is", ""+newVal);
		
	}
	
	public void setImageToDefault(){
		profile_pic = getResources().getDrawable(R.drawable.default_profile_pic);
		pp.setImageDrawable(profile_pic);
	}
	
	private String convertPF(Bitmap a){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		a.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		String apath = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
		return apath;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		/*it checked for the key that is received
		 * if received mReq_ID from the browse button action
		 */
		case mReq_ID:
			if (resultCode == RESULT_OK){
				Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.moveToFirst();
                String yu = cursor.getString(columnIndex);
                cursor.close();
                
                //Bitmap photo = BitmapFactory.decodeFile(filePath);
                Options options = null;
                photo = BitmapFactory.decodeFile(yu,options);
                path = convertPF(photo);
                mPrefsEdit.putString("profilePic",path).commit();
                
			}
			break;
		//if received mCamReq_ID from the take photo button action
		case mCamReq_ID:
			if (resultCode == RESULT_OK){
				Bundle extras = data.getExtras();
				photo = (Bitmap) extras.get("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();         
			    photo.compress(Bitmap.CompressFormat.JPEG,100,stream);
                path = convertPF(photo);
                mPrefsEdit.putString("profilePic",path).commit();
			}
			break;
		default:
			break;
		}
	}
	
	// please define following two methods in your activity
    public Bitmap decodeSampledBitmapFromByte(byte[] res,
                int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(res, 0, res.length,options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(res, 0, res.length,options);
        }
    
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }
            return inSampleSize;
	}
    
    
    
    

}
