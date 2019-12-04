package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("FBID")
    private int fBID;

    @SerializedName("FoodId")
    private int foodId;

    @SerializedName("FoodName")
    private String foodName;

    @SerializedName("Price")
    private double price;

    @SerializedName("FoodImage")
    private String foodImage;

    @SerializedName("RestaurantId")
    private int restaurantId;

    @SerializedName("RestaurantName")
    private String restaurantName;

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setFBID(int fBID) {
        this.fBID = fBID;
    }

    public int getFBID() {
        return fBID;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    @Override
    public String toString() {
        return
                "Favorite{" +
                        "foodImage = '" + foodImage + '\'' +
                        ",price = '" + price + '\'' +
                        ",fBID = '" + fBID + '\'' +
                        ",restaurantId = '" + restaurantId + '\'' +
                        ",foodId = '" + foodId + '\'' +
                        ",restaurantName = '" + restaurantName + '\'' +
                        ",foodName = '" + foodName + '\'' +
                        "}";
    }
}