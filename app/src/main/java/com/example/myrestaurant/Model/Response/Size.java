package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Size {

	@SerializedName("id")
	private int id;

	@SerializedName("description")
	private String description;

	@SerializedName("extraPrice")
	private double extraPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getExtraPrice() {
		return extraPrice;
	}

	public void setExtraPrice(double extraPrice) {
		this.extraPrice = extraPrice;
	}

	@Override
 	public String toString(){
		return 
			"Size{" +
			"extraPrice = '" + extraPrice + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}