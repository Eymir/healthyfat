package com.myhealthmemo;

import java.util.Arrays;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class WeightTrackerHomeFragment extends Fragment {
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private ProgressBar mProgressBar;
	XYPlot mySimpleXYPlot;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_tracker_home_fragment, container, false);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(getActivity(), R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mPrefsEdit = mPrefs.edit();
		if(mPrefs.getBoolean("targetweightnotset", true)){
			//Display AlertDialog
			//mPrefsEdit.putBoolean("targetweightnotset", false).commit();
		}
		
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
    	mProgressBar.setMax(30);
    	mProgressBar.setProgress(10);
    	
    	mySimpleXYPlot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
    		 
    	// Create two arrays of y-values to plot:	
    	Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
    	Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
    			
    	XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), // SimpleXYSeries takes a List so turn our array into a List
    	SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
    	"Series1" );                             // Set the display title of the series
    	XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, 
    		"Series2");
    			
    	LineAndPointFormatter series1Format = new LineAndPointFormatter(
    	Color.rgb(0, 200, 0),                   // line color
    	Color.rgb(0, 100, 0),                   // point color
    	Color.rgb(150, 190, 150),null);         // fill color (optional)
    		
    	mySimpleXYPlot.addSeries(series1, series1Format);
    	mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), Color.rgb(150, 150, 190),null));
    			
    	// Reduce the number of range labels
    	mySimpleXYPlot.setTicksPerRangeLabel(3);
    		    
    	// By default, AndroidPlot displays developer guides to aid in laying out your plot.
    	// To get rid of them call disableAllMarkup():
    	mySimpleXYPlot.getBackgroundPaint().setColor(getResources().getColor(R.color.background));
    	mySimpleXYPlot.getGraphWidget().getBackgroundPaint().setAlpha(0);
    	mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setAlpha(0);
    	mySimpleXYPlot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeOriginLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        
        
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
