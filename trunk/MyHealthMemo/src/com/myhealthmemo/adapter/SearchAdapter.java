package com.myhealthmemo.adapter;

import com.myhealthmemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] searchList;
	
	public SearchAdapter(Context context, String[]searchList) {
		super(context, R.layout.dummy_diet_fragment_search, searchList);
		this.context =context;
		this.searchList=searchList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.dummy_diet_fragment_search, parent, false);
		TextView search = (TextView) rowView.findViewById(R.id.list);
		search.setText(searchList[position]);
		
		return rowView;
  }
}