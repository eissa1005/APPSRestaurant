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
	private String isSize;

	@SerializedName("isAddon")
	private String isAddon;

	@SerializedName("description")
	private String description;

	@SerializedName("discount")
	private int discount;

	@SerializedName("MenuId")
	private String menuId;


	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setMenuId(String menuId){
		this.menuId = menuId;
	}

	public String getMenuId(){
		return menuId;
	}

	public void setIsSize(String isSize){
		this.isSize = isSize;
	}

	public String getIsSize(){
		return isSize;
	}

	public void setPrice(Double price){
		this.price = price;
	}

	public Double getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIsAddon(String isAddon){
		this.isAddon = isAddon;
	}

	public String getIsAddon(){
		return isAddon;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDiscount(int discount){
		this.discount = discount;
	}

	public int getDiscount(){
		return discount;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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