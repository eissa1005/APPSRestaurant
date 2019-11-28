package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class Addon {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("extraPrice")
    private float extraPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(float extraPrice) {
        this.extraPrice = extraPrice;
    }

    @Override
    public String toString() {
        return
                "Addon{" +
                        "extraPrice = '" + extraPrice + '\'' +
                        ",name = '" + name + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}