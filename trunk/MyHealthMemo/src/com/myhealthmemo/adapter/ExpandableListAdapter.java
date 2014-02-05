package com.myhealthmemo.adapter;

import java.util.List;
import java.util.Map;
import com.myhealthmemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	  private Activity context;
	  private Map<String, List<String>> MealsCollections;
	  private List<String> Meals;
	  
	  public ExpandableListAdapter(Activity context, List<String> Meals,
	            Map<String, List<String>> MealsCollections) {
		  this.context = context;
		  this.MealsCollections = MealsCollections;
		  this.Meals = Meals;
	  }
	  
	  @Override
	  public Object getChild(int groupPosition, int childPosititon) {
		  return this.MealsCollections.get(this.Meals.get(groupPosition))
           .get(childPosititon);
	  }
	  
	  @Override
	  public long getChildId(int groupPosition, int childPosition) {
		  return childPosition;
	  }
	  
	  @Override
	  public View getChildView(int groupPosition, final int childPosition,
			  boolean isLastChild, View convertView, ViewGroup parent) {
		  final String childText = (String) getChild(groupPosition, childPosition);
		  if (convertView == null) {
			  LayoutInflater infalInflater = (LayoutInflater) this.context
					  .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			  convertView = infalInflater.inflate(R.layout.dummy_diet_fragment_list_item, null);
		  }
		  TextView txtListChild = (TextView) convertView
				  .findViewById(R.id.lblListItem);

		  txtListChild.setText(childText);
		  return convertView;
	  }
	  
	  @Override
	  public int getChildrenCount(int groupPosition) {
		  return this.MealsCollections.get(this.Meals.get(groupPosition))
				  .size();
	  }
	  
	  @Override
	  public Object getGroup(int groupPosition) {
		  return this.Meals.get(groupPosition);
	  }
	  
	  @Override
	  public int getGroupCount() {
		  return this.Meals.size();
	  }
	  
	  @Override
	  public long getGroupId(int groupPosition) {
		  return groupPosition;
	  }
	  
	  @Override
	  public View getGroupView(int groupPosition, boolean isExpanded,
			  View convertView, ViewGroup parent) {
		  String headerTitle = (String) getGroup(groupPosition);
		  if (convertView == null) {
			  LayoutInflater infalInflater = (LayoutInflater) this.context
					  .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			  convertView = infalInflater.inflate(R.layout.dummy_diet_fragment_list_group, null);
		  }

		  TextView lblListHeader = (TextView) convertView
				  .findViewById(R.id.lblmeals);
		  lblListHeader.setTypeface(null, Typeface.BOLD);
		  lblListHeader.setText(headerTitle);

		  return convertView;
	  }
	  
	  @Override
	  public boolean hasStableIds() {
		  return false;
	  }
	  
	  @Override
	  public boolean isChildSelectable(int groupPosition, int childPosition) {
		  return true;
	  }

}

