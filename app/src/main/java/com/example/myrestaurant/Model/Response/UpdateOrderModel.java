package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private String result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

}
