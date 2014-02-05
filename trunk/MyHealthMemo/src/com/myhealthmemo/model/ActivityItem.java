package com.myhealthmemo.model;

public class ActivityItem {
	private int icon;
	private String title;
	private String value;

	private boolean isGroupHeader = false;
 
	public ActivityItem(String title, String value) {
		this(-1,title,value);
		isGroupHeader = true;
	}
 
	public ActivityItem(int icon, String title, String value) {
		super();
		this.icon = icon;
		this.title = title;
		this.value = value;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public boolean isGroupHeader(){
		return this.isGroupHeader;
	}
}
