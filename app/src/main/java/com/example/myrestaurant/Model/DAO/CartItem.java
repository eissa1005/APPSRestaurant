package com.example.myrestaurant.Model.DAO;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {

    @PrimaryKey
    @Nullable
    @ColumnInfo(name = "foodId")
    private int foodId;
    @ColumnInfo(name = "foodName")
    private String foodName;

    @ColumnInfo(name = "foodImage")
    private String foodImage;

    @ColumnInfo(name = "foodPrice")
    private Double foodPrice;

    @ColumnInfo(name = "foodQuantity")
    private int foodQuantity;

    @ColumnInfo(name = "userPhone")
    private String userPhone;

    @ColumnInfo(name = "restaurantId")
    private int restaurantId;

    @ColumnInfo(name = "foodAddon")
    private boolean foodAddon;

    @ColumnInfo(name = "foodSize")
    private boolean foodSize;

    @ColumnInfo(name = "foodExtraPrice")
    private Double foodExtraPrice;

    @ColumnInfo(name = "fbid")
    private String fbid;

    public CartItem() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean getFoodAddon() {
        return foodAddon;
    }

    public void setFoodAddon(boolean foodAddon) {
        this.foodAddon = foodAddon;
    }

    public boolean getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(boolean foodSize) {
        this.foodSize = foodSize;
    }

    public Double getFoodExtraPrice() {
        return foodExtraPrice;
    }

    public void setFoodExtraPrice(Double foodExtraPrice) {
        this.foodExtraPrice = foodExtraPrice;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }
}
