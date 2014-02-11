package com.myhealthmemo.model;

import android.graphics.Bitmap;

public class SearchItem {
	private Bitmap icon;
    private String title;
    
    
    private boolean isGroupHeader = false;
    public SearchItem(){
    	isGroupHeader = true;
    }
    
	public SearchItem(Bitmap blabla, String gg) {
		super();
		this.icon = blabla;
		this.title = gg;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
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
