package com.example.myrestaurant.Model.Response;


import com.google.gson.annotations.SerializedName;

public class Restaurant {

	@SerializedName("RestaurantId")
	private int restaurantId;

	@SerializedName("RestaurantName")
	private String restaurantName;

	@SerializedName("RestaurantAddress")
	private String restaurantAddress;

	@SerializedName("phone")
	private String phone;

	@SerializedName("image")
	private String image;

	@SerializedName("lat")
	private double lat;

	@SerializedName("lng")
	private double lng;

	@SerializedName("userOwner")
	private String userOwner;

	@SerializedName("paymentUrl")
	private String paymentUrl;

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(String userOwner) {
		this.userOwner = userOwner;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	@Override
 	public String toString(){
		return 
			"Result{" +
			"restaurantAddress = '" + restaurantAddress + '\'' + 
			",image = '" + image + '\'' + 
			",lng = '" + lng + '\'' + 
			",userOwner = '" + userOwner + '\'' + 
			",phone = '" + phone + '\'' + 
			",restaurantId = '" + restaurantId + '\'' + 
			",paymentUrl = '" + paymentUrl + '\'' + 
			",restaurantName = '" + restaurantName + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}