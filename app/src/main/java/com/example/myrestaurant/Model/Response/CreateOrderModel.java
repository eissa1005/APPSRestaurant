package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateOrderModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<CreateOrder> result;

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

    public List<CreateOrder> getResult() {
        return result;
    }

    public void setResult(List<CreateOrder> result) {
        this.result = result;
    }

}
