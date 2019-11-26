package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Foods{

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("price")
	private int price;

	@SerializedName("image")
	private String image;

	@SerializedName("isSize")
	private Object isSize;

	@SerializedName("isAddon")
	private Object isAddon;

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

	public void setIsSize(Object isSize){
		this.isSize = isSize;
	}

	public Object getIsSize(){
		return isSize;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIsAddon(Object isAddon){
		this.isAddon = isAddon;
	}

	public Object getIsAddon(){
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
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