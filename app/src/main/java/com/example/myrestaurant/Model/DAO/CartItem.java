package com.example.myrestaurant.Model.DAO;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "FoodId")
    private int FoodId;

    @ColumnInfo(name = "FoodName")
    private String FoodName;

    @ColumnInfo(name = "FoodImage")
    private String FoodImage;

    @ColumnInfo(name = "FoodPrice")
    private Double FoodPrice;

    @ColumnInfo(name = "FoodQuantity")
    private int FoodQuantity;

    @ColumnInfo(name = "userPhone")
    private String userPhone;

    @ColumnInfo(name = "RestaurantId")
    private int RestaurantId;

    @ColumnInfo(name = "FoodAddon")
    private String FoodAddon;

    @ColumnInfo(name = "FoodSize")
    private String FoodSize;

    @ColumnInfo(name = "FoodExtraPrice")
    private Double FoodExtraPrice;

    @ColumnInfo(name = "FBID")
    private String FBID;


    public CartItem() {
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public Double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        FoodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return FoodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        FoodQuantity = foodQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getFoodAddon() {
        return FoodAddon;
    }

    public void setFoodAddon(String foodAddon) {
        FoodAddon = foodAddon;
    }

    public String getFoodSize() {
        return FoodSize;
    }

    public void setFoodSize(String foodSize) {
        FoodSize = foodSize;
    }

    public Double getFoodExtraPrice() {
        return FoodExtraPrice;
    }

    public void setFoodExtraPrice(Double foodExtraPrice) {
        FoodExtraPrice = foodExtraPrice;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }
}
