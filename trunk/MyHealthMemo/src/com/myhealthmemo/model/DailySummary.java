package com.myhealthmemo.model;

public class DailySummary {
private String ds_Date;
private int total_Calories_Intake;
private int total_Calories_Burned;
private double old_Weight;
private double new_Weight;
private double weight_Change;
private int rda_Remaining;

public DailySummary(){
	
}
public DailySummary(String ds_Date, int total_Calories_Intake,int total_Calories_Burned, double old_Weight, double new_Weight,double weight_Change, int rda_Remaining) {
	super();
	this.ds_Date = ds_Date;
	this.total_Calories_Intake = total_Calories_Intake;
	this.total_Calories_Burned = total_Calories_Burned;
	this.old_Weight = old_Weight;
	this.new_Weight = new_Weight;
	this.weight_Change = weight_Change;
	this.rda_Remaining = rda_Remaining;
}
public String getDs_Date() {
	return ds_Date;
}
public void setDs_Date(String ds_Date) {
	this.ds_Date = ds_Date;
}
public int getTotal_Calories_Intake() {
	return total_Calories_Intake;
}
public void setTotal_Calories_Intake(int total_Calories_Intake) {
	this.total_Calories_Intake = total_Calories_Intake;
}
public int getTotal_Calories_Burned() {
	return total_Calories_Burned;
}
public void setTotal_Calories_Burned(int total_Calories_Burned) {
	this.total_Calories_Burned = total_Calories_Burned;
}
public double getOld_Weight() {
	return old_Weight;
}
public void setOld_Weight(double old_Weight) {
	this.old_Weight = old_Weight;
}
public double getNew_Weight() {
	return new_Weight;
}
public void setNew_Weight(double new_Weight) {
	this.new_Weight = new_Weight;
}
public double getWeight_Change() {
	return weight_Change;
}
public void setWeight_Change(double weight_Change) {
	this.weight_Change = weight_Change;
}
public int getRda_Remaining() {
	return rda_Remaining;
}
public void setRda_Remaining(int rda_Remaining) {
	this.rda_Remaining = rda_Remaining;
}


}
