package com.myhealthmemo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
	private RelativeLayout height_c, weight_c, education_c, school_c, classes_c, reg_no_c, activity_level_c;
	private Button mBtn;
	private RadioButton mRB,mRB2;
	private String w;
	private String[] mPriSch, mSecSch, nums;
	private double daily_calories_need,bmr,wp,hp,ap;
	
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
		String path = mPrefs.getString("profilePic", "");
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
		height.setText(mPrefs.getString("height", ""));
		weight.setText(mPrefs.getString("weight", ""));
		bmi.setText(mPrefs.getString("bmi", ""));
		education.setText(mPrefs.getString("education", ""));
		school.setText(mPrefs.getString("school", ""));
		classes.setText(mPrefs.getString("class", ""));
		reg_no.setText(mPrefs.getString("reg_no", ""));
		activity_level.setText(mPrefs.getString("activity_level", ""));
	}
	
	//A method to assign listener to corresponding layout variable
	private void setClickListener(){
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
	
	//Display Height Dialog
	private void showHeightDialog() {
		final Dialog mDialog = new Dialog(UserProfileActivity.this);
		mDialog.setTitle("Update Height");
		mDialog.setContentView(R.layout.number_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		int a = Integer.parseInt(mPrefs.getString("height", ""));
		mNumPk.setMaxValue(200);
		mNumPk.setMinValue(0);
		mNumPk.setValue(a);
		mNumPk.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mPrefsEdit.putString("height", String.valueOf(mNumPk.getValue()));
				String w = mPrefs.getString("weight", "");
				mPrefsEdit.putString("bmi", calculateBMI(String.valueOf(mNumPk.getValue()),w));
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
				mPrefsEdit.putString("bmi", calculateBMI(h, String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue())));
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
				mPrefsEdit.putString("activity_level", spn.getSelectedItem().toString()).commit();
				String h = mPrefs.getString("height", "");
				String w = mPrefs.getString("weight", "");
				mPrefsEdit.putString("daily_calories_need", calculateDCN(h,w));
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

}
