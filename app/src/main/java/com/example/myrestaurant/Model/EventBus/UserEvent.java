package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.Users;

public class UserEvent {
    private boolean success;
    private String message;
    private Users user;

    public UserEvent(boolean success, Users user) {
        this.success = success;
        this.user = user;
    }

    public UserEvent(boolean success, String message) {
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
