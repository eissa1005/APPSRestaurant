package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.User;

public class UserEvent {
    private boolean success;
    private String message;
    private User user;

    public UserEvent(boolean success, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
