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
import com.myhealthmemo.model.ActivityItem;
import com.myhealthmemo.model.SettingsItem;

public class ActivityArrayAdapter extends ArrayAdapter<ActivityItem> {
	private final Context context;
	private final ArrayList<ActivityItem> activityArrayList;
 
    public ActivityArrayAdapter(Context context, ArrayList<ActivityItem> activityArrayList){
   	 super(context, R.layout.dummy_activity_list, activityArrayList);
   	 this.context = context;
        this.activityArrayList = activityArrayList;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
   	 // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // 2. Get rowView from inflater
        
        View rowView = null;
        if(!activityArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.dummy_activity_list, parent, false);

            // 3. Get icon,title & counter views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            TextView valueView = (TextView) rowView.findViewById(R.id.item_title2);

            // 4. Set the text for textView 
            imgView.setImageResource(activityArrayList.get(position).getIcon());
            titleView.setText(activityArrayList.get(position).getTitle());
            titleView.setText(activityArrayList.get(position).getValue());
        }
        else{
                rowView = inflater.inflate(R.layout.group_header_item, parent, false);
                TextView titleView = (TextView) rowView.findViewById(R.id.header);
                titleView.setText(activityArrayList.get(position).getTitle());

        }

        // 5. return rowView
        return rowView;
    }
}
