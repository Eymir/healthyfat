package com.myhealthmemo.model;

public class DietDiaryDetails {
	private String dd_Date;
 	private String f_ID;
 	private double serving_Size;
 	private int calories_Intake;
 	private String meal_Type;

 	public DietDiaryDetails() {
		
	}
 	
 	public DietDiaryDetails(String dd_Date, String f_ID, double serving_Size,int calories_Intake, String meal_Type) {
 		super();
 		this.dd_Date = dd_Date;
 		this.f_ID = f_ID;
 		this.serving_Size = serving_Size;
 		this.calories_Intake = calories_Intake;
 		this.meal_Type = meal_Type;
 	}	


 	public String getDd_Date() {
 		return dd_Date;
 	}


 	public void setDd_Date(String dd_Date) {
 		this.dd_Date = dd_Date;
 	}

 	public String getF_ID() {
 		return f_ID;
 	}

 	public void setF_ID(String f_ID) {
 		this.f_ID = f_ID;
 	}

 	public double getServing_Size() {
 		return serving_Size;
 	}

 	public void setServing_Size(double serving_Size) {
 		this.serving_Size = serving_Size;
 	}

 	public int getCalories_Intake() {
 		return calories_Intake;
 	}


 	public void setCalories_Intake(int calories_Intake) {
 		this.calories_Intake = calories_Intake;
 	}


 	public String getMeal_Type() {
 		return meal_Type;
 	}


 	public void setMeal_Type(String meal_Type) {
 		this.meal_Type = meal_Type;
 	}

}
