package com.myhealthmemo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myhealthmemo.R;
import com.myhealthmemo.model.MealsItem;

public class SettingMealAdapter extends ArrayAdapter<MealsItem> {
	 private final Context context;
     private final ArrayList<MealsItem> mealsArrayList;
     
     public SettingMealAdapter(Context context, ArrayList<MealsItem> mealsArrayList){
    	 super(context, R.layout.target_item, mealsArrayList);
    	 this.context = context;
         this.mealsArrayList = mealsArrayList;
     }
     @Override
     public View getView(int position, View convertView, ViewGroup parent){
    	 // 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         // 2. Get rowView from inflater
         View rowView = inflater.inflate(R.layout.target_item2, parent, false);
         // 3. Get icon,title & counter views from the rowView
         ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
         ImageView imgView2 = (ImageView) rowView.findViewById(R.id.item_icon2);
         TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

         // 4. Set the text for textView 
         imgView.setImageResource(mealsArrayList.get(position).getIcon());
         titleView.setText(mealsArrayList.get(position).getTitle());
         
         return rowView;
     }
     

}
