package com.myhealthmemo.adapter;

import java.util.ArrayList;
import com.myhealthmemo.R;
import com.myhealthmemo.model.SettingsItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsAdapter extends ArrayAdapter<SettingsItem> {
	 private final Context context;
     private final ArrayList<SettingsItem> settingsArrayList;
     
     public SettingsAdapter(Context context, ArrayList<SettingsItem> settingsArrayList){
    	 super(context, R.layout.target_item, settingsArrayList);
    	 this.context = context;
         this.settingsArrayList = settingsArrayList;
     }
     
     @Override
     public View getView(int position, View convertView, ViewGroup parent){
    	 // 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
         // 2. Get rowView from inflater
         
         View rowView = null;
         if(!settingsArrayList.get(position).isGroupHeader()){
             rowView = inflater.inflate(R.layout.target_item, parent, false);

             // 3. Get icon,title & counter views from the rowView
             ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
             TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

             // 4. Set the text for textView 
             imgView.setImageResource(settingsArrayList.get(position).getIcon());
             titleView.setText(settingsArrayList.get(position).getTitle());
         }
         else{
                 rowView = inflater.inflate(R.layout.group_header_item, parent, false);
                 TextView titleView = (TextView) rowView.findViewById(R.id.header);
                 titleView.setText(settingsArrayList.get(position).getTitle());

         }

         // 5. return rowView
         return rowView;
     }
}
