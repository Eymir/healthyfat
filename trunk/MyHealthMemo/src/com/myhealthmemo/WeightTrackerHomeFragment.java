package com.myhealthmemo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.myhealthmemo.piechart.MultitouchPlot;

public class WeightTrackerHomeFragment extends Fragment implements NumberPicker.OnValueChangeListener{
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private ProgressBar mProgressBar;
	XYPlot mySimpleXYPlot;
	private Button mBtn;
	private TextView tcw_Value, remaining_Value, starting_Value, present_Value, target_Value;
	private RelativeLayout tutorialView;
	private double daily_calories_need,bmr,wp,hp,ap;
	private String[] nums;
	private String w;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_tracker_home_fragment, container, false);
		tcw_Value = (TextView) rootView.findViewById(R.id.tcw_Value);
		remaining_Value = (TextView) rootView.findViewById(R.id.remaining_Value);
		starting_Value = (TextView) rootView.findViewById(R.id.starting_Value);
		present_Value = (TextView) rootView.findViewById(R.id.present_Value);
		target_Value = (TextView) rootView.findViewById(R.id.target_Value);
		tutorialView = (RelativeLayout) rootView.findViewById(R.id.tutorialView);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(getActivity(), R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mPrefsEdit = mPrefs.edit();
		if(mPrefs.getBoolean("targetweightnotset", true)){
			tcw_Value.setText("00.0 Kg");
			remaining_Value.setText("00.0 Kg");
			starting_Value.setText("00.0 Kg");
			present_Value.setText("00.0 Kg");
			target_Value.setText("00.0 Kg");
			tutorialView.setVisibility(View.VISIBLE);
			showAlert("Attention!","Please proceed to set your target weight before proceeding...");
		} 

    	
		MultitouchPlot multitouchPlot = (MultitouchPlot) rootView.findViewById(R.id.multitouchPlot);
        // initialize our XYPlot reference:
		// Create two arrays of y-values to plot:
		Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
		Number[] series2Numbers = {4, 6, 3, 8, 2, 10};


		// Turn the above arrays into XYSeries:
		XYSeries series1 = new SimpleXYSeries(
            Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
            "Series1");                             // Set the display title of the series

		// Same as above, for series2
		XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Series2");

		// Create a formatter to use for drawing a series using LineAndPointRenderer:
		LineAndPointFormatter series1Format = new LineAndPointFormatter(
            Color.rgb(0, 200, 0),                   // line color
            Color.rgb(0, 100, 0),                   // point color
            Color.TRANSPARENT,null);              // fill color (optional)

		// Add series1 to the xyplot:
		multitouchPlot.addSeries(series1, series1Format);

		// Same as above, with series2:
		multitouchPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100),
            Color.TRANSPARENT,null));

		// Reduce the number of range labels
		multitouchPlot.setTicksPerRangeLabel(3);

		// By default, AndroidPlot displays developer guides to aid in laying out your plot.
		// To get rid of them call disableAllMarkup():
		//multitouchPlot.disableAllMarkup();

		multitouchPlot.setRangeBoundaries(0, 10, BoundaryMode.FIXED);
		multitouchPlot.setDomainBoundaries(0, 2.2, BoundaryMode.FIXED);
        setHasOptionsMenu(true);
        
		return rootView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(!mPrefs.getBoolean("targetweightnotset",true)){
			tutorialView.setVisibility(View.GONE);
			String starting = mPrefs.getString("starting_weight", "") + " Kg";
			String present = mPrefs.getString("weight", "") + " Kg";
			String target = mPrefs.getString("targeted_weight", "") + " Kg";
			
			starting_Value.setText(starting);
			present_Value.setText(present);
			target_Value.setText(target);
			String[] starting_split = starting.split("\\s+");
			String[] present_split = present.split("\\s+");
			String[] target_split = target.split("\\s+");
			if (isEmpty(starting_split[0])||isEmpty(target_split[0])){
				starting_split[0] = "0.0";
				target_split[0] = "0.0";
			}
			BigDecimal starting_weight = new BigDecimal(starting_split[0]);
			BigDecimal present_weight = new BigDecimal(present_split[0]);
			BigDecimal target_weight = new BigDecimal(target_split[0]);
			BigDecimal weight_change = starting_weight.subtract(present_weight);
			BigDecimal weight_remaining = present_weight.subtract(target_weight);
			BigDecimal max_weight_to_lose = starting_weight.subtract(target_weight);
			
			int res = target_weight.compareTo(present_weight);
			String remaining_weight = null;

			if(res==1){
				double remove_negative = Double.parseDouble(weight_remaining.toString());
				remaining_weight = String.valueOf(remove_negative);
			} else {
				remaining_weight = weight_remaining.toString() + " Kg";
			}
			
			String total_weight_change = weight_change.toString() + " Kg";
			
			tcw_Value.setText(total_weight_change);
			remaining_Value.setText(remaining_weight);
	    	mProgressBar.setMax(max_weight_to_lose.intValue());
	    	mProgressBar.setProgress(weight_change.intValue());
	    	setHasOptionsMenu(true);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight_tracker_home, menu);
	}
	
	public static WeightTrackerHomeFragment newInstance(String text) {

		WeightTrackerHomeFragment f = new WeightTrackerHomeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
       
        return f;
    }
	
	//Display Alert
	public void showAlert(String a, String b){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		//set title
		alertDialogBuilder.setTitle(a);
		//set dialog message
		alertDialogBuilder
			.setMessage(b)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					showWeightDialog();
							
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
				}
		});

		
		//create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		//show it
		alertDialog.show();
	}
	
	@Override
	public  boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.edit:
			final TextView ucw, stw;
			final Dialog mDialog = new Dialog(getActivity());
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setContentView(R.layout.weight_dialog);
			mBtn = (Button) mDialog.findViewById(R.id.done_button);
			ucw = (TextView) mDialog.findViewById(R.id.change_present_weight);
			stw = (TextView) mDialog.findViewById(R.id.set_target_weight);
			ucw.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					mDialog.dismiss();
					showWeightDialog2();
					
				}
			});
			
			stw.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					mDialog.dismiss();
					showWeightDialog();
				}
			});
			
			mBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					mDialog.dismiss();
				}
			});
			
			mDialog.show();
			
		default:
	   		break;
   		}
		return super.onOptionsItemSelected(item);
   	}
	
	
		
	//Display Weight Dialog
	private void showWeightDialog() {
		final Dialog mDialog = new Dialog(getActivity());
		mDialog.setTitle("Set Target Weight");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		mNumPk.setMaxValue(100);
		mNumPk.setMinValue(20);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
		mNumPk.setOnValueChangedListener(this);
		mNumPk2.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String current_weight = mPrefs.getString("weight", "");
				String target_weight = String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue());
				String weight_status = mPrefs.getString("weight_status", "");
				BigDecimal cw = new BigDecimal(current_weight);
				BigDecimal tw = new BigDecimal(target_weight);
				int res = tw.compareTo(cw);
				boolean a = true;
				if(weight_status.equals("Severely Underweight") || weight_status.equals("Underweight")){
					if (res==-1){
						mDialog.dismiss();
						showAlert("Attention", "You are currently " + weight_status + "and your target weight should not be less than your current weight. "
								+ "You should either start by maintaining current weight or gain weight safely");
						a = false;
					} else {
						String results = calculateWeightStatus(target_weight);
						if(results.equals("Severely Overweight") || results.equals("Overweight")){
							mDialog.dismiss();
							showAlert("Attention", "Your target weight should not gained within the unhealthy range of Severely Overweight"
										+ "and Overweight");
							a= false;
						}
					}
				} else if (weight_status.equals("Acceptable Weight")){
					String results = calculateWeightStatus(target_weight);
					if(!results.equals("Acceptable Weight")){
						mDialog.dismiss();
						showAlert("Attention", "You are currently " + weight_status + "and you should be trying to maintain your weight "
								+ "or try to keep your weight within the BMI's acceptable range");
						a = false;
					}
					
				} else if (weight_status.equals("Overweight") || weight_status.equals("Severely Overweight")){
					if (res==1){
						mDialog.dismiss();
						showAlert("Attention", "You are currently " + weight_status + "and your target weight should not be more than your current weight. "
								+ "You should either start by maintaining current weight or lose weight safely");
						a = false;
					} else {
						String results = calculateWeightStatus(target_weight);
						if(results.equals("Severely Underweight") || results.equals("Underweight")){
							mDialog.dismiss();
							showAlert("Attention", "Your target weight should not dropped within the unhealthy range of Severely Underweight"
										+ "and Underweight");
							a= false;
						}
					}
				}
				if(a==true){
					mPrefsEdit.putString("starting_weight", mPrefs.getString("weight", ""));
					mPrefsEdit.putString("targeted_weight", target_weight);
					mPrefsEdit.putBoolean("targetweightnotset",false);
					mDialog.dismiss();
					mPrefsEdit.commit();
					tutorialView.setVisibility(View.GONE);
					onResume();
				}
			}
			
			
		});
		mDialog.show();
	}

	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
			
	}
	//Determine if String is empty
	public boolean isEmpty(String a){
		if (a.matches("")){
			return true;
		} else {
			return false;
		}
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
	
	public String calculateWeightStatus(String p){
		int age = calculateAge(mPrefs.getString("dob", ""));
		String bmi = calculateBMI(mPrefs.getString("height", ""),p);
		double body = Double.parseDouble(bmi);
		String weight_status = "";
		if(mPrefs.getString("gender", "").equals("Male") ) {
			if(age == 6 ){
				if (body < 12.9){	
					weight_status = "Severely Underweight";		
				} else if (body >=12.9 && body < 13.2){		
					weight_status = "Underweight";
				} else if (body >=13.2 && body < 18.9){
					weight_status = "Acceptable Weight";
				} else if (body >= 18.9 && body <21.5 ){
					weight_status = "Overweight";
				} else if (body >=21.5 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 7){
				if(body <13.1){
					weight_status = "Severely Underweight";
				} else if (body >=13.1 && body <13.3 ){
					weight_status = "Underweight";
				} else if (body >=13.3 && body <19.7 ){
					weight_status = "Acceptable Weight";
				} else if(body >=19.7 && body < 23.1 ){
					weight_status = "Overweight";
				} else if (body >=23.1 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 8){
				if(body <13.3   ){
					weight_status = "Severely Underweight";
				} else if (body >=13.3 && body <13.6  ){
					weight_status = "Underweight";
				} else if (body >=13.6 && body <20.9  ){
					weight_status = "Acceptable Weight";
				} else if(body >=20.9 && body <24.7  ){
					weight_status = "Overweight";
				} else if (body >= 24.7){
					weight_status = "Severely Overweight";
				}
			} else if (age == 9){
				if(body <13.5){
					weight_status = "Severely Underweight";
				} else if (body >=13.5 && body <13.9 ){
					weight_status = "Underweight";
				} else if (body >=13.9 && body <21.9){
					weight_status = "Acceptable Weight";
				} else if(body >=21.9 && body <26.1){
					weight_status = "Overweight";
				} else if (body >=26.1){
					weight_status = "Severely Overweight";
				}
			} else if (age == 10){
				if(body <13.9){
					weight_status = "Severely Underweight";
				} else if (body >=13.9 &&  body <14.4){
					weight_status = "Underweight";
				} else if (body >=14.4 && body < 22.8 ){
					weight_status = "Acceptable Weight";
				} else if(body >=22.8 && body < 27.4){
					weight_status = "Overweight";
				} else if (body >=27.4 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 11){
				if(body <14.2){
					weight_status = "Severely Underweight";
				} else if (body >= 14.2 && body < 14.6){
					weight_status = "Underweight";
				} else if (body >=14.6 && body < 23.8){
					weight_status = "Acceptable Weight";
				} else if(body >=23.8 && body < 28.4){
					weight_status = "Overweight";
				} else if (body >=28.4){
					weight_status = "Severely Overweight";
				}
			} else if (age == 12){
				if(body <14.6){
					weight_status = "Severely Underweight";
				} else if (body >=14.6 && body <14.9){
					weight_status = "Underweight";
				} else if (body >=14.9 && body <24.5){
					weight_status = "Acceptable Weight";
				} else if(body >=24.5 && body <29.3){
					weight_status = "Overweight";
				} else if (body >=29.3){
					weight_status = "Severely Overweight";
				}
			} else if (age == 13){
				if(body <14.8){
					weight_status = "Severely Underweight";
				} else if (body >=14.8 && body < 15.2){
					weight_status = "Underweight";
				} else if (body >=15.2 && body < 25.1){
					weight_status = "Acceptable Weight";
				} else if(body >=25.1 && body < 30.1){
					weight_status = "Overweight";
				} else if (body >=30.1){
					weight_status = "Severely Overweight";
				}
			}else if (age == 14){
				if(body <15.1){
					weight_status = "Severely Underweight";
				} else if (body >=15.1 && body <15.5){
					weight_status = "Underweight";
				} else if (body >=15.5 && body < 25.6){
					weight_status = "Acceptable Weight";
				} else if(body >=25.6 && body < 30.7){
					weight_status = "Overweight";
				} else if (body >=30.7){
					weight_status = "Severely Overweight";
				}
			} else if (age == 15){
				if(body <15.4){
					weight_status = "Severely Underweight";
				} else if (body >=15.4 && body < 15.9){
					weight_status = "Underweight";
				} else if (body >=15.9 && body <26.2){
					weight_status = "Acceptable Weight";
				} else if(body >=26.2 && body < 31.3){  
					weight_status = "Overweight";
				} else if (body >=31.3){
					weight_status = "Severely Overweight";	
				}
			} else if (age == 16){
				if(body <15.7){				
					weight_status = "Severely Underweight";
				} else if (body >=15.7 && body <16.2 ){
					weight_status = "Underweight";
				} else if (body >=16.2 && body <26.6 ){
					weight_status = "Acceptable Weight";
				} else if(body >=26.6 && body <31.8 ){
					weight_status = "Overweight";
				} else if (body >=31.8){
					weight_status = "Severely Overweight";
				}
			} else if (age == 17){
				if(body <16  ){
					weight_status = "Severely Underweight";
				} else if (body >=16 && body < 16.4){
					weight_status = "Underweight";
				} else if (body >=16.4 && body < 27.1){
					weight_status = "Acceptable Weight";
				} else if(body >= 27 && body < 32.2){
					weight_status = "Overweight";
				} else if (body >=32.2){
					weight_status = "Severely Overweight";
				}
			} else if (age == 18){
				if(body <16.2  ){
					weight_status = "Severely Underweight";
				} else if (body >=16.2 && body < 16.6 ){
					weight_status = "Underweight";
				} else if (body >=16.6 && body < 27.5 ){
					weight_status = "Acceptable Weight";
				} else if(body >=27.5 && body <32.5 ){
					weight_status = "Overweight";
				} else if (body >=32.5 ){
					weight_status = "Severely Overweight";
				}
			}
		} else if (mPrefs.getString("gender", "").equals("Female")){
			if(age == 6 ){	
				if (body <12.7){	
					weight_status = "Severely Underweight";		
				} else if (body >=12.7 && body < 13){
					weight_status = "Underweight";
				} else if (body >=13 && body< 18.7){	 
					weight_status = "Acceptable Weight";
				} else if (body >= 18.7 && body < 20.9  ){
					weight_status = "Overweight";
				} else if (body >=20.9 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 7){
				if (body <12.8){
					weight_status = "Severely Underweight";
				} else if (body >=12.8 && body< 13.5 ){
					weight_status = "Underweight";
				} else if (body >=13.5 && body< 19.2 ){		 	 
					weight_status = "Acceptable Weight";
				} else if (body >= 19.2 && body< 22   ){
					weight_status = "Overweight";
				} else if (body>=22  ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 8){
				if (body <13.2){
					weight_status = "Severely Underweight";
				} else if (body >=13.2 && body<13.6 ){
					weight_status = "Underweight";
				} else if (body >= 13.6 && body< 20.2  ){
					weight_status = "Acceptable Weight";
				} else if (body >= 20.2 && body< 23.2    ){
					weight_status = "Overweight";
				} else if (body>=23.2  ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 9){
				if (body <13.6){
					weight_status = "Severely Underweight";
				} else if (body >=13.6 && body<13.9 ){
					weight_status = "Underweight";
				} else if (body >= 13.9 && body< 21.1  ){
					weight_status = "Acceptable Weight";
				} else if (body >= 21.1 && body< 24.7    ){
					weight_status = "Overweight";
				} else if (body>= 24.7 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 10){
				if (body <13.8){
					weight_status = "Severely Underweight";
				} else if (body >=13.8 && body<14.2 ){
					weight_status = "Underweight";
				} else if (body >= 14.2 && body< 22  ){ 
					weight_status = "Acceptable Weight";
				} else if (body >= 22 && body< 26.8 ){
					weight_status = "Overweight";	
				} else if (body>= 26.8 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 11){	
				if (body <14){
					weight_status = "Severely Underweight";
				} else if (body >=14 && body< 14.5){
					weight_status = "Underweight";
				} else if (body >= 14.5 && body< 22.8 ){
					weight_status = "Acceptable Weight";
				} else if (body >= 22.8 && body< 26.8 ){
					weight_status = "Overweight";
				} else if (body>= 26.8 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 12){
				if (body <14.3){
					weight_status = "Severely Underweight";
				 } else if (body >=14.3 && body <15){
					 weight_status = "Underweight";
				 } else if (body >= 15 && body< 23.7 ){
					 weight_status = "Acceptable Weight";
				 } else if (body >= 23.7 && body< 27.7){
					 weight_status = "Overweight";
				 } else if (body>= 27.7 ){
					 weight_status = "Severely Overweight";
				 }
			} else if (age == 13){
				if (body <15){
					weight_status = "Severely Underweight";
				} else if (body >=15 && body <15.3){
					weight_status = "Underweight";
				} else if (body >= 15.3 && body< 24){
					weight_status = "Acceptable Weight";
				} else if (body > 24.7 && body< 28.3){
					weight_status = "Overweight";
				} else if (body>= 28.3 ){
					weight_status = "Severely Overweight";
				}
			} else if (age == 14){
				if (body <15.2){
					weight_status = "Severely Underweight";
				} else if (body >=15.2 && body< 15.8){
					weight_status = "Underweight";
				} else if (body >= 15.8 && body< 24.7){ 
					weight_status = "Acceptable Weight";
				} else if (body > 24.7 && body< 29){
					weight_status = "Overweight";
				} else if (body>= 29){
					weight_status = "Severely Overweight";
				}
			} else if (age == 15){
				if (body <15.3){
					weight_status = "Severely Underweight";
				} else if (body >=15.3 && body< 15.9){
					weight_status = "Underweight";
				} else if (body >= 15.9 && body< 25){
					weight_status = "Acceptable Weight";
				} else if (body >= 25 && body< 29.5){
					weight_status = "Overweight";
				} else if (body>= 29.5){
					weight_status = "Severely Overweight";
				}
			} else if (age == 16){
				if (body <15.7){
					weight_status = "Severely Underweight";
				} else if (body >=15.7 && body< 16.2){
					weight_status = "Underweight";
				} else if (body >= 16.2 && body< 25.7){
					weight_status = "Acceptable Weight";
				} else if (body >= 25.7 && body< 30.1 ){
					weight_status = "Overweight";
				} else if (body>= 30.1){
					weight_status = "Severely Overweight";
				}
			} else if (age == 17){
				if (body <16){
					weight_status = "Severely Underweight";
				} else if (body >=16 && body< 16.3){
					weight_status = "Underweight";
				} else if (body >= 16.3 && body< 25.8){
					weight_status = "Acceptable Weight";
				} else if (body >= 25.8 && body< 30 ){
					weight_status = "Overweight";
				} else if (body>= 30){
					weight_status = "Severely Overweight";
				}
			} else if (age == 18){
				if (body <16.1){
					weight_status = "Severely Underweight";
				} else if (body >=16.1 && body< 16.8){
					weight_status = "Underweight";
				} else if (body >= 16.8 && body< 26){
					weight_status = "Acceptable Weight";
				} else if (body >= 26 && body< 30.2 ){
					weight_status = "Overweight";
				} else if (body>= 30.2 ){
					weight_status = "Severely Overweight";
				}
			}	
		}
		return weight_status;
	}
	
	//Display Weight Dialog
		private void showWeightDialog2() {
			final Dialog mDialog = new Dialog(getActivity());
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
		
	
		
		
	
}
