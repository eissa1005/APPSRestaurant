package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;


public class Category {

	@SerializedName("MenuId")
	private int menuId;

	@SerializedName("name")
	private String name;

	@SerializedName("image")
	private String image;

	@SerializedName("description")
	private String description;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
 	public String toString(){
		return 
			"Result{" +
			"image = '" + image + '\'' + 
			",menuId = '" + menuId + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}