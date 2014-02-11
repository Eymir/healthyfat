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
import com.myhealthmemo.model.ActivityListItem;

public class ActivityListAdapter extends ArrayAdapter<ActivityListItem>{
	private final Context context;
    private final ArrayList<ActivityListItem> activityListItem;
    
    public ActivityListAdapter(Context context, ArrayList<ActivityListItem> activityListItem){
   	 	super(context, R.layout.target_item, activityListItem);
   	 	this.context = context;
        this.activityListItem = activityListItem;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
   	 // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // 2. Get rowView from inflater
        
        View rowView = null;
 
        rowView = inflater.inflate(R.layout.target_item, parent, false);

        // 3. Get icon,title & counter views from the rowView
        ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

        // 4. Set the text for textView 
        imgView.setImageBitmap(activityListItem.get(position).getIcon());
        titleView.setText(activityListItem.get(position).getTitle());

        // 5. return rowView
        return rowView;
    }
}
