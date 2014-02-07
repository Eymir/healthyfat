package com.myhealthmemo.model;

public class Ingredient {
	private int i_ID;
	private String ingredient_Name;
	private String ingredient_Type;
	private int calories_Per_Serving_Size;
	private double per_Serving_In_Gram;
 
	public Ingredient() {
		
	}

	public Ingredient(int i_ID, String ingredient_Name, String ingredient_Type,int calories_Per_Serving_Size, double per_Serving_In_Gram) {
		super();
		this.i_ID = i_ID;
		this.ingredient_Name = ingredient_Name;
		this.ingredient_Type = ingredient_Type;
		this.calories_Per_Serving_Size = calories_Per_Serving_Size;
		this.per_Serving_In_Gram = per_Serving_In_Gram;
	}

	public int getI_ID() {
		return i_ID;
	}

	public void setI_ID(int i_ID) {
		this.i_ID = i_ID;
	}

	public String getIngredient_Name() {
		return ingredient_Name;
	}

	public void setIngredient_Name(String ingredient_Name) {
		this.ingredient_Name = ingredient_Name;
	}

	public String getIngredient_Type() {
		return ingredient_Type;
	}

	public void setIngredient_Type(String ingredient_Type) {
		this.ingredient_Type = ingredient_Type;
	}

	public int getCalories_Per_Serving_Size() {
		return calories_Per_Serving_Size;
	}

	public void setCalories_Per_Serving_Size(int calories_Per_Serving_Size) {
		this.calories_Per_Serving_Size = calories_Per_Serving_Size;
	}

	public double getPer_Serving_In_Gram() {
		return per_Serving_In_Gram;
	}

	public void setPer_Serving_In_Gram(double per_Serving_In_Gram) {
		this.per_Serving_In_Gram = per_Serving_In_Gram;
	}
 
}
