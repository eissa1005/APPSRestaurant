package com.example.myrestaurant.Model.EventBus;

import com.example.myrestaurant.Model.Response.Addon;

import java.util.List;

public class AddonLoadEvent {

    private boolean succcess;
    private List<Addon> addonList;

    public AddonLoadEvent(boolean succcess, List<Addon> addonList) {
        this.succcess = succcess;
        this.addonList = addonList;
    }

    public boolean isSucccess() {
        return succcess;
    }

    public void setSucccess(boolean succcess) {
        this.succcess = succcess;
    }

    public List<Addon> getAddonList() {
        return addonList;
    }

    public void setAddonList(List<Addon> addonList) {
        this.addonList = addonList;
    }
}
