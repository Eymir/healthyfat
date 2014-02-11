package com.myhealthmemo.adapter;

import java.util.ArrayList;

import com.myhealthmemo.R;
import com.myhealthmemo.model.SearchItem;
import com.myhealthmemo.model.SettingsItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<SearchItem> {
	private final Context context;
	private final ArrayList<SearchItem> searchList;
	
	public SearchAdapter(Context context, ArrayList<SearchItem> searchList) {
		super(context, R.layout.dummy_diet_fragment_search, searchList);
		this.context =context;
		this.searchList=searchList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	     View rowView = null;
         if(!searchList.get(position).isGroupHeader()){
             rowView = inflater.inflate(R.layout.target_item, parent, false);

             // 3. Get icon,title & counter views from the rowView
             ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
             TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

             // 4. Set the text for textView 
             imgView.setImageBitmap(searchList.get(position).getIcon());
             titleView.setText(searchList.get(position).getTitle());
         }
         else{
                 rowView = inflater.inflate(R.layout.group_header_item, parent, false);
                 TextView titleView = (TextView) rowView.findViewById(R.id.header);
                 titleView.setText(searchList.get(position).getTitle());

         }
		return rowView;
}
}