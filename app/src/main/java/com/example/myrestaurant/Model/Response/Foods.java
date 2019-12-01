package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Foods{

	@SerializedName("FoodID")
	private int foodID;

	@SerializedName("Name")
	private String name;

	@SerializedName("Description")
	private String description;

	@SerializedName("Price")
	private double price;

	@SerializedName("Image")
	private String image;

	@SerializedName("IsSize")
	private boolean isSize;

	@SerializedName("IsAddon")
	private boolean isAddon;

	@SerializedName("Discount")
	private int discount;

	public int getFoodID() {
		return foodID;
	}

	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isSize() {
		return isSize;
	}

	public void setSize(boolean size) {
		isSize = size;
	}

	public boolean isAddon() {
		return isAddon;
	}

	public void setAddon(boolean addon) {
		isAddon = addon;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@Override
 	public String toString(){
		return 
			"Food{" +
			"image = '" + image + '\'' +
			",isSize = '" + isSize + '\'' +
			",price = '" + price + '\'' +
			",name = '" + name + '\'' +
			",isAddon = '" + isAddon + '\'' +
			",description = '" + description + '\'' +
			",discount = '" + discount + '\'' +
			",id = '" + foodID + '\'' +
			"}";
		}
}