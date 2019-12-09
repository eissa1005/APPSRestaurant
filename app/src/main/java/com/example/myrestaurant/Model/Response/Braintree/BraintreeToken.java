package com.example.myrestaurant.Model.Response.Braintree;

public class BraintreeToken {
    private String clientToken;
    private boolean success;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
