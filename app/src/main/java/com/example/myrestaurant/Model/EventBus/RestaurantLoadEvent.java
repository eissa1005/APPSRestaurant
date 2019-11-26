package com.example.myrestaurant.Model.EventBus;


import com.example.myrestaurant.Model.Response.Restaurant;

import java.util.List;

public class RestaurantLoadEvent {

    private boolean success;
    private String message;
    private List<Restaurant> mRestaurantList;

    public RestaurantLoadEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RestaurantLoadEvent(boolean success, List<Restaurant> restaurantList) {
        this.success = success;
        mRestaurantList = restaurantList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Restaurant> getRestaurantList() {
        return mRestaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        mRestaurantList = restaurantList;
    }
}
