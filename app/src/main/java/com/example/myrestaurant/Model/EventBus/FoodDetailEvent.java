package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.Foods;

public class FoodDetailEvent {

    private boolean success;
    private Foods foods;

    public FoodDetailEvent(boolean success, Foods foods) {
        this.success = success;
        this.foods = foods;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Foods getFoods() {
        return foods;
    }

    public void setFoods(Foods foods) {
        this.foods = foods;
    }
}
