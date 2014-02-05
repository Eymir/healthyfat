package com.myhealthmemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myhealthmemo.R;

public class MealsArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] mealslist;
 
	
	public MealsArrayAdapter(Context context, String[] mealslist) {
		super(context, R.layout.dummy_diet_fragment_list_menu, mealslist);
		this.context =context;
		this.mealslist=mealslist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		View rowView = inflater.inflate(R.layout.dummy_diet_fragment_list_menu, parent, false);
		TextView meals = (TextView) rowView.findViewById(R.id.meal_list);
		meals.setText(mealslist[position]);
	
		return rowView;
	}
}
