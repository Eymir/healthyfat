package com.myhealthmemo.model;

public class MealsItem {
	private int icon;
	private int icon2;
	private String title;

	private boolean isGroupHeader = false;
 
	public MealsItem(String title) {
		this(-1,-1,title);
		isGroupHeader = true;
	}
 
	public MealsItem(int icon, int icon2, String title) {
		super();
		this.icon = icon;
		this.icon2 = icon2;
		this.title = title;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public int getIcon2() {
		return icon2;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public void setIcon2(int icon) {
		this.icon2 = icon;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isGroupHeader(){
		return this.isGroupHeader;
	}
}
