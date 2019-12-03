package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.Foods;

public class FoodDetailEvent {
    private boolean success;
    private String message;
    private Foods food;

    public FoodDetailEvent(boolean success, Foods food) {
        this.success = success;
        this.food = food;
    }

    public FoodDetailEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public Foods getFood() {
        return food;
    }

    public void setFood(Foods food) {
        this.food = food;
    }
}
