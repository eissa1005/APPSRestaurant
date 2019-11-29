package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Foods{

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("price")
	private Double price;

	@SerializedName("image")
	private String image;

	@SerializedName("isSize")
	private boolean isSize;

	@SerializedName("isAddon")
	private boolean isAddon;

	@SerializedName("description")
	private String description;

	@SerializedName("discount")
	private int discount;

	@SerializedName("MenuId")
	private String menuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Override
 	public String toString(){
		return 
			"Food{" +
			"image = '" + image + '\'' + 
			",menuId = '" + menuId + '\'' + 
			",isSize = '" + isSize + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",isAddon = '" + isAddon + '\'' + 
			",description = '" + description + '\'' + 
			",discount = '" + discount + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}