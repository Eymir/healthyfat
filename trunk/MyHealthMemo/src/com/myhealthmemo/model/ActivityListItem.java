package com.myhealthmemo.model;

import android.graphics.Bitmap;

public class ActivityListItem {
	private Bitmap icon;
	private String title;
	private boolean isGroupHeader = false;
	
	public ActivityListItem(){}
	
	public ActivityListItem(Bitmap icon, String title){
		super();
		this.icon = icon;
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public Bitmap getIcon(){
		return this.icon;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(Bitmap icon){
		this.icon = icon;
	}
}
