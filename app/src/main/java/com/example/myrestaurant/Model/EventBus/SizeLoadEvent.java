package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.Size;

import java.util.List;

public class SizeLoadEvent {

    private boolean success;
    private String message;
    private List<Size>  sizeList;

    public SizeLoadEvent(boolean success, List<Size> sizeList) {
        this.success = success;
        this.sizeList = sizeList;
    }

    public SizeLoadEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

