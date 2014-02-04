package com.myhealthmemo.model;

public class Food {

private String F_ID;
  private String Food_name;
  private double Carbohydrates;
  private double Protein;
  private  double Dietary_Fibre;
  private double Sodium;
  private double Fats;
  private String Meat_Category_ID;
  private  String Vegetable_Category_ID;
  private String Rice_Cereal_Category_ID;

  public Food() {
		
	}
  public Food(String Food_name, double Carbohydrates,double Protein,double Dietary_Fibre,double Sodium,double Fats,String Meat_Category_ID,String Vegetable_Category_ID, String Rice_Cereal_Category_ID){
	  this.Food_name= Food_name;
	  this.Carbohydrates=Carbohydrates;
	  this.Protein=Protein;
	  this.Dietary_Fibre=Dietary_Fibre;
	  this.Sodium = Sodium;
	  this.Fats =Fats;
	  this.Meat_Category_ID = Meat_Category_ID;
	  this.Vegetable_Category_ID = Vegetable_Category_ID;
	  this.Rice_Cereal_Category_ID = Rice_Cereal_Category_ID;
  }
  
  public String getF_ID() {
	return F_ID;
}
public void setF_ID(String f_ID) {
	F_ID = f_ID;
}
public String getFood_name() {
	return Food_name;
}
public void setFood_name(String food_name) {
	Food_name = food_name;
}
public double getCarbohydrates() {
	return Carbohydrates;
}
public void setCarbohydrates(double carbohydrates) {
	Carbohydrates = carbohydrates;
}
public double getProtein() {
	return Protein;
}
public void setProtein(double protein) {
	Protein = protein;
}
public double getDietary_Fibre() {
	return Dietary_Fibre;
}
public void setDietary_Fibre(double dietary_Fibre) {
	Dietary_Fibre = dietary_Fibre;
}
public double getSodium() {
	return Sodium;
}
public void setSodium(double sodium) {
	Sodium = sodium;
}
public double getFats() {
	return Fats;
}
public void setFats(double fats) {
	Fats = fats;
}
public String getMeat_Category_ID() {
	return Meat_Category_ID;
}
public void setMeat_Category_ID(String meat_Category_ID) {
	Meat_Category_ID = meat_Category_ID;
}
public String getVegetable_Category_ID() {
	return Vegetable_Category_ID;
}
public void setVegetable_Category_ID(String vegetable_Category_ID) {
	Vegetable_Category_ID = vegetable_Category_ID;
}
public String getRice_Cereal_Category_ID() {
	return Rice_Cereal_Category_ID;
}
public void setRice_Cereal_Category_ID(String rice_Cereal_Category_ID) {
	Rice_Cereal_Category_ID = rice_Cereal_Category_ID;
}				
}
