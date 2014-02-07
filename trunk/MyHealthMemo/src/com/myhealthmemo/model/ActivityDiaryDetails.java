package com.myhealthmemo.model;

public class ActivityDiaryDetails {
	private String ad_Date;
	private int a_Id;
	private int duration;
	private int calories_Burned;
	private int distance;
	private double speed;

	public ActivityDiaryDetails(){
	
	}

	public ActivityDiaryDetails(String ad_Date, int a_Id, int duration,int calories_Burned, int distance, double speed) {
		super();
		this.ad_Date = ad_Date;
		this.a_Id = a_Id;
		this.duration = duration;
		this.calories_Burned = calories_Burned;
		this.speed = speed;
	}

	public String getAd_Date() {
		return ad_Date;
	}

	public void setAd_Date(String ad_Date) {
		this.ad_Date = ad_Date;
	}

	public int getA_Id() {
		return a_Id;
	}

	public void setA_Id(int a_Id) {
		this.a_Id = a_Id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCalories_Burned() {
		return calories_Burned;
	}

	public void setCalories_Burned(int calories_Burned) {
		this.calories_Burned = calories_Burned;
	}	
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
}
