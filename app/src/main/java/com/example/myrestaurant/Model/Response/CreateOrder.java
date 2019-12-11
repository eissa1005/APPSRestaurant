package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class CreateOrder {

    @SerializedName("OrderNumber")
    private int orderNumber;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    @Override
    public String toString(){
        return
                "CreateOrder{" +
                        "orderNumber = '" + orderNumber + '\'' +
                        "}";
    }
}
