/*package com.myhealthmemo.adapter;

import java.util.ArrayList;

import com.myhealthmemo.model.MealsItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myhealthmemo.R;;

public class SettingMealAdapter extends ArrayAdapter<MealsItem> {
	 private final Context context;
     private final ArrayList<MealsItem> mealsArrayList;
     
     public SettingMealAdapter(Context context, ArrayList<MealsItem> settingsArrayList){
    	 super(context, R.layout.target_item, settingsArrayList);
    	 this.context = context;
         this.mealsArrayList = mealsArrayList;
     }
     @Override
     public View getView(int position, View convertView, ViewGroup parent){
    	 // 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }
     
     // 2. Get rowView from inflater
     
     View rowView = null;
     // 3. Get icon,title & counter views from the rowView
     ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
     TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

     // 4. Set the text for textView 
     imgView.setImageResource(mealsArrayList.get(position).getIcon());
     titleView.setText(settingsArrayList.get(position).getTitle());
  }
}*/
