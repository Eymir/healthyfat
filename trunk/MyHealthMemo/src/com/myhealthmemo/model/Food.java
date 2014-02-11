package com.myhealthmemo.model;

public class Food {

private int f_ID;
	private String food_Name;
	private double calories;
	private double carbohydrates;
	private double protein;
	private double dietary_Fibre;
	private double sodium;
	private double fats;
	private double cholestrol;
	private String image;

	public Food() {
		
	}

	public Food(int f_ID, String food_Name, double calories, double carbohydrates,double protein, double dietary_Fibre, double sodium, double fats, double cholestrol, String image) {
		super();
		this.f_ID = f_ID;
		this.food_Name = food_Name;
		this.calories = calories;
		this.carbohydrates = carbohydrates;
		this.protein = protein;
		this.dietary_Fibre = dietary_Fibre;
		this.sodium = sodium;
		this.fats = fats;
		this.cholestrol = cholestrol;
		this.image = image;
	
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getF_ID() {
	  	return f_ID;
	}

	public void setF_ID(int f_ID) {
		this.f_ID = f_ID;
	}

	public String getFood_Name() {
		return food_Name;
	}

	public void setFood_Name(String food_Name) {
		this.food_Name = food_Name;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}	

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}
	
	

	public double getDietary_Fibre() {
		return dietary_Fibre;
	}

	public void setDietary_Fibre(double dietary_Fibre) {
		this.dietary_Fibre = dietary_Fibre;
	}

	public double getSodium() {
		return sodium;
	}

 	public void setSodium(double sodium) {
 		this.sodium = sodium;
 	}

 	public double getFats() {
 		return fats;
 	}

 	public void setFats(double fats) {
 		this.fats = fats;
 	}
 
 	public double getCalories() {
 		return calories;
 	}
 	
 	public void setCalories(double calories) {
 		this.calories = calories;
 	}
 	
 	public double getCholestrol() {
 		return cholestrol;
 	}
 	
 	public void setCholestrol(double cholestrol) {
 		this.cholestrol = cholestrol;
 	}
}
