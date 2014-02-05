package com.myhealthmemo.model;

public class SettingsItem {
	private int icon;
    private String title;
 
    private boolean isGroupHeader = false;
 
    public SettingsItem(String title) {
        this(-1,title);
        isGroupHeader = true;
    }
    public SettingsItem(int icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }
    
    public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public boolean isGroupHeader(){
		return this.isGroupHeader;
	}
	
	
}
