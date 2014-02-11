package com.myhealthmemo.model;

public class Food {

private int f_ID;
	private String food_Name;
	private double carbohydrates;
	private double protein;
	private double dietary_Fibre;
	private double sodium;
	private double fats;
	private String meat_Category_ID;
	private  String vegetable_Category_ID;
	private String rice_Cereal_Category_ID;
	private byte[] image;

	public Food() {
		
	}

	public Food(int f_ID, String food_Name, double carbohydrates,double protein, double dietary_Fibre, double sodium, double fats, byte[] image) {
		super();
		this.f_ID = f_ID;
		this.food_Name = food_Name;
		this.carbohydrates = carbohydrates;
		this.protein = protein;
		this.dietary_Fibre = dietary_Fibre;
		this.sodium = sodium;
		this.fats = fats;
		this.image = image;
	
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
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
  
 	public String getMeat_Category_ID() {
 		return meat_Category_ID;
 	}

 	public void setMeat_Category_ID(String meat_Category_ID) {
 		this.meat_Category_ID = meat_Category_ID;
 	}	

 	public String getVegetable_Category_ID() {
 		return vegetable_Category_ID;
 	}
 	
 	public void setVegetable_Category_ID(String vegetable_Category_ID) {
 		this.vegetable_Category_ID = vegetable_Category_ID;
 	}

 	public String getRice_Cereal_Category_ID() {
 		return rice_Cereal_Category_ID;
 	}

 	public void setRice_Cereal_Category_ID(String rice_Cereal_Category_ID) {
 		this.rice_Cereal_Category_ID = rice_Cereal_Category_ID;
 	}
}
