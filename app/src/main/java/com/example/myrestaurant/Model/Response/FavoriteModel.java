package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<Favorite> result;

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

    public List<Favorite> getResult() {
        return result;
    }

    public void setResult(List<Favorite> result) {
        this.result = result;
    }
}
