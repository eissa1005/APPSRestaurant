package com.example.myrestaurant.Model.EventBus;

public class CalculatePriceEvent {

    private  int foodQuantity;

    public CalculatePriceEvent(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }
}
