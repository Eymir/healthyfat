package com.myhealthmemo.model;

public class Activity {
	private int a_Id;
	private String activity_Name;
	private int met_Value;
	private String activity_Image;


	public Activity(){
	
	}

	public Activity(int a_Id, String activity_Name, int met_Value,String activity_Image) {
		super();
		this.a_Id = a_Id;
		this.activity_Name = activity_Name;
		this.met_Value = met_Value;
		this.activity_Image = activity_Image;
	}

	public int getA_Id() {
		return a_Id;
	}

	public void setA_Id(int a_Id) {
		this.a_Id = a_Id;
	}

	public String getActivity_Name() {
		return activity_Name;
	}

	public void setActivity_Name(String activity_Name) {
		this.activity_Name = activity_Name;
	}

	public int getMet_Value() {
		return met_Value;
	}

	public void setMet_Value(int met_Value) {
		this.met_Value = met_Value;
	}

	public String getActivity_Image() {
		return activity_Image;
	}

	public void setActivity_Image(String activity_Image) {
		this.activity_Image = activity_Image;
	}	
}
