package com.myhealthmemo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class Pre2Activity extends Activity implements NumberPicker.OnValueChangeListener, OnItemSelectedListener, 
	OnFocusChangeListener, OnClickListener, OnCheckedChangeListener {

	private SharedPreferences mPrefs;
	private Intent mIntent;
	private EditText mhEdit,mwEdit,mcEdit,mrnEdit;
	private Spinner mSpinner;
	private AutoCompleteTextView mAuto;
	private Button mBtn;
	private String w;
	private String[] mPriSch,mSecSch,nums;
	private RadioGroup mGroup;
	private RadioButton mRB,mRB2;
	private SharedPreferences.Editor mPrefsEdit;
	private double daily_calories_need,bmr,wp,hp,ap;
	private int whole,decimal,number;
	private boolean isNull,isNull2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre2);
		// Show the Up button in the action bar.
		setupActionBar();
		init();	
		setPriAdapter();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_level_arrays, R.layout.dropdown);
		adapter.setDropDownViewResource(R.layout.dropdown);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(Pre2Activity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		hasPreference();
		setListener();
	}
	
	//A method to initialize each of the variable with the corresponding layout in xml
	private void init(){
		mhEdit = (EditText) findViewById(R.id.height_Edit);
		mwEdit = (EditText) findViewById(R.id.weight_Edit);
		mAuto = (AutoCompleteTextView) findViewById(R.id.school_Edit);
		mGroup = (RadioGroup) findViewById(R.id.radio_Education);
		mcEdit = (EditText) findViewById(R.id.class_Edit);
		mrnEdit = (EditText) findViewById(R.id.reg_no_Edit);
		mSpinner = (Spinner) findViewById(R.id.activity_level_Edit);
		mPriSch = getResources()
				.getStringArray(R.array.primary_school_arrays);
		mSecSch = getResources()
				.getStringArray(R.array.secondary_school_arrays);
	}
	
	//A method to assign listener to corresponding layout variable
	private void setListener(){
		mhEdit.setOnFocusChangeListener(this);
		mhEdit.setOnClickListener(this);
		mwEdit.setOnFocusChangeListener(this);
		mwEdit.setOnClickListener(this);
		mGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_timer2, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//saved the instance of whatever the user has entered before going back to PreActivity
			isNull = isEmpty(mhEdit.getText().toString());
			if(!isNull){
				mPrefsEdit.putString("height", mhEdit.getText().toString());
			} 

			isNull2 = isEmpty(mwEdit.getText().toString());
			if (!isNull2){
				mPrefsEdit.putString("weight", mwEdit.getText().toString());
				
			} 
			
			if(!isNull && !isNull2){
				mPrefsEdit.putString("bmi", calculateBMI(mhEdit.getText().toString(),mwEdit.getText().toString()));
				mPrefsEdit.putString("daily_calories_need", calculateDCN(mhEdit.getText().toString(),mwEdit.getText().toString()));
			}
			
			isNull = isEmpty(mAuto.getText().toString());
			if(!isNull){
				mPrefsEdit.putString("school", mAuto.getText().toString());
			} 
						
			isNull = isEmpty(mcEdit.getText().toString());
			if (isNull){
				mPrefsEdit.putString("class", mcEdit.getText().toString());
			} 
							
			isNull = isEmpty(mrnEdit.getText().toString());
			if (isNull){
				mPrefsEdit.putString("reg_no", mrnEdit.getText().toString());
			} 
									
			switch (mGroup.getCheckedRadioButtonId()) {
			case R.id.radio_primary:
				mRB = (RadioButton) findViewById(R.id.radio_primary);
				break;
			case R.id.radio_secondary:
				mRB = (RadioButton) findViewById(R.id.radio_secondary);
				break;
			default:
				break;
			} 				
			mPrefsEdit.putString("education", mRB.getText().toString());
			mPrefsEdit.putString("activity_level", mSpinner.getSelectedItem().toString());
			mPrefsEdit.commit();
			finish();
			break;
		case R.id.accept:
			/*
			 * All external loop determines if any of the EditText field is Empty
			 * if any of the field is Empty, display AlertDialog
			 * if not, proceed to assign value to preference key
			 * and commit
			 * 
			 */
			isNull = isEmpty(mhEdit.getText().toString());
			if(isNull){
				showAlert();
			} else {
				isNull = isEmpty(mwEdit.getText().toString());
				if (isNull){
					showAlert();
				} else {
					isNull = isEmpty(mAuto.getText().toString());
					if(isNull){
						showAlert();
					} else {
						isNull = isEmpty(mcEdit.getText().toString());
						if (isNull){
							showAlert();
						} else {
							isNull = isEmpty(mrnEdit.getText().toString());
							if (isNull){
								showAlert();
							} else {
								mPrefsEdit.putString("height", mhEdit.getText().toString());
								mPrefsEdit.putString("weight", mwEdit.getText().toString());	
								mPrefsEdit.putString("bmi", calculateBMI(mhEdit.getText().toString(),mwEdit.getText().toString()));
								mPrefsEdit.putString("daily_calories_need", calculateDCN(mhEdit.getText().toString(),mwEdit.getText().toString()));
								mPrefsEdit.putString("weight_status", calculateWeightStatus());
								switch (mGroup.getCheckedRadioButtonId()) {
								case R.id.radio_primary:
									mRB = (RadioButton) findViewById(R.id.radio_primary);
									break;
								case R.id.radio_secondary:
									mRB = (RadioButton) findViewById(R.id.radio_secondary);
									break;
								default:
									break;
								} 
								mPrefsEdit.putString("education", mRB.getText().toString());
								mPrefsEdit.putString("school", mAuto.getText().toString());
								mPrefsEdit.putString("class", mcEdit.getText().toString());
								mPrefsEdit.putString("reg_no", mrnEdit.getText().toString());
								mPrefsEdit.putString("activity_level", mSpinner.getSelectedItem().toString());
							
								mIntent = new Intent (this, NavDrawerActivity.class);
								startActivity(mIntent);
								/*Set the "firstrun" boolean case to false before commit
								 * on next startup, application will not be able to navigates to
								 * PreActivity and Pre2Activity anymore
								 */
								mPrefsEdit.putBoolean("firstrun", false);
								mPrefsEdit.commit();
								
							}
						}
					}
				}
			}
			break;
			
		}
		return true;
	}
	
	/*
     * A method to determine if there is any preference value for any of the fields
     * This is to handle the case where the user click on the back button then proceed again to this activity
     * it works like a savedinstance.
     */
	private void hasPreference(){
		isNull = isEmpty(mPrefs.getString("height", ""));
		if(!isNull){
			mhEdit.setText(mPrefs.getString("height",""));
		}
		isNull = isEmpty(mPrefs.getString("weight", ""));
		if(!isNull){
			mwEdit.setText(mPrefs.getString("weight", ""));
		}
		isNull = isEmpty(mPrefs.getString("education",""));
		if(!isNull){
			mRB = (RadioButton) findViewById(R.id.radio_primary);
			mRB2 = (RadioButton) findViewById(R.id.radio_secondary);
			if(mPrefs.getString("education","").equals("Primary")){
				mRB.setChecked(true);
				mRB2.setChecked(false);
			}
			else{
				mRB.setChecked(false);
				mRB2.setChecked(true);
			}
		}
		isNull = isEmpty(mPrefs.getString("school", ""));
		if(!isNull){
			mAuto.setText(mPrefs.getString("school", ""));
		}
		isNull = isEmpty(mPrefs.getString("class", ""));
		if(!isNull){
			mcEdit.setText(mPrefs.getString("class",""));
		}
		isNull = isEmpty(mPrefs.getString("reg_no", ""));
		if(!isNull){
			mrnEdit.setText(mPrefs.getString("reg_no",""));
		}
		isNull = isEmpty(mPrefs.getString("activity_level", ""));
		if(!isNull){
			if (mPrefs.getString("activity_level", "").equals("little or no exercise")){
				mSpinner.setSelection(0);
			}
			else if (mPrefs.getString("activity_level", "").equals("light exercise/sports")){
				mSpinner.setSelection(1);
			}
			else if (mPrefs.getString("activity_level", "").equals("moderate exercise/sports")){
				mSpinner.setSelection(2);
			}
			else if (mPrefs.getString("activity_level", "").equals("hard exercise/sports")){
				mSpinner.setSelection(3);
			}
			else if (mPrefs.getString("activity_level", "").equals("very hard exercise/sports")){
				mSpinner.setSelection(4);
			}
		}
	}
	
	@Override
    public void onFocusChange(View v, boolean hasFocus) {
		switch(v.getId()){
		case R.id.height_Edit:
			if(hasFocus){
				showHeightDialog();
			} 
			break;
		case R.id.weight_Edit:
			if(hasFocus){
				showWeightDialog();
			} 
			break;
		default:
			break;
		}
		
		
    }
	
	@Override
    public void onClick(View v) {
    	switch (v.getId()){
    	case R.id.height_Edit:
    		showHeightDialog();
    		break;
    	case R.id.weight_Edit:
    		showWeightDialog();
    		break;
    	}
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(group.getId()){
		case R.id.radio_Education:
			switch (checkedId) {
    		case R.id.radio_primary:
    			setPriAdapter();
    			break;
    		case R.id.radio_secondary:
    			setSecAdapter();
    			break;
    		default:
    			break;
    		}
			break;
    	default:
    		break;
		}
    }
	
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		Log.i("value is", ""+newVal);
	}
	
	//Display Height Dialog
	private void showHeightDialog() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set Height");
		mDialog.setContentView(R.layout.number_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		mNumPk.setMaxValue(200);
		mNumPk.setMinValue(100);
		if(!isEmpty(mhEdit.getText().toString())){
			number = Integer.parseInt(mhEdit.getText().toString());
			mNumPk.setValue(number);
		}
		mNumPk.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mhEdit.setText(String.valueOf(mNumPk.getValue()));
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	//Display Weight Dialog
	private void showWeightDialog() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set Weight");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		mNumPk.setMaxValue(100);
		mNumPk.setMinValue(20);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
		if(!isEmpty(mwEdit.getText().toString())){
			w = mwEdit.getText().toString();
			nums =  w.split("\\.");
			whole = Integer.parseInt(nums[0]);
			decimal = Integer.parseInt(nums[1]);
			mNumPk.setValue(whole);
			mNumPk2.setValue(decimal);
		}
		mNumPk.setOnValueChangedListener(this);
		mNumPk2.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mwEdit.setText(String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue()));
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		parent.getItemAtPosition(pos);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	//Determine if String is empty
	public boolean isEmpty(String a){
		if (a.matches("")){
			return true;
		} else {
			return false;
		}
	}
	
	//Display Alert
	public void showAlert(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		//set title
		alertDialogBuilder.setTitle("Attention!");
		//set dialog message
		alertDialogBuilder
		.setMessage("Please fill up all required fields before proceeding...")
		.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*
				 * if this button is clicked, just close
				 * the dialog box and do nothing
				 */
				dialog.cancel();
				
			}
		});
		//create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		//show it
		alertDialog.show();
	}
	
	//set adapter to include Primary School Data
	public void setPriAdapter(){
		mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mPriSch));
	}
	
	//set adapter to include Secondary School Data
	public void setSecAdapter(){
		mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mSecSch));
	}
	
	//A method to calculate the BMI
	public String calculateBMI(String height, String weight){
		BigDecimal kg_weight = new BigDecimal(weight);
		BigDecimal cm_height = new BigDecimal(height);
		BigDecimal hundred = new BigDecimal("100");
		BigDecimal m_height = cm_height.divide(hundred,1,RoundingMode.HALF_UP);
		BigDecimal m_height_sq = m_height.multiply(m_height);
		BigDecimal bmi = kg_weight.divide(m_height_sq,1,RoundingMode.HALF_UP);
		return bmi.toString();
	}
	
	//A method to determine the age of the user
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
	
	//A method to calculate the Daily Calories Need of the user
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
		
		if (mSpinner.getSelectedItem().toString().equals("little or no exercise")){
			daily_calories_need = bmr * 1.2;
		}
		else if (mSpinner.getSelectedItem().toString().equals("light exercise/sports")){
			daily_calories_need = bmr * 1.375;
		}
		else if (mSpinner.getSelectedItem().toString().equals("moderate exercise/sports")){
			daily_calories_need = bmr * 1.55;
		}
		else if (mSpinner.getSelectedItem().toString().equals("hard exercise/sports")){
			daily_calories_need = bmr * 1.725;
		}
		else{
			daily_calories_need = bmr * 1.9;
		}
		int int_dcn = (int) Math.round(daily_calories_need);
		return String.valueOf(int_dcn);
	}
	
	public String calculateWeightStatus(){
		int age = calculateAge(mPrefs.getString("dob", ""));
		String bmi = calculateBMI(mhEdit.getText().toString(),mwEdit.getText().toString());
		double body = Double.parseDouble(bmi);
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

}
