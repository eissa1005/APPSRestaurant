package com.example.myrestaurant.Common;

import com.example.myrestaurant.Model.Response.Addon;
import com.example.myrestaurant.Model.Response.Restaurant;
import com.example.myrestaurant.Model.Response.User;

import java.util.HashSet;
import java.util.Set;

public class Common {

    public   static final String API_KEY="1234";
    public  static final String baseURL="http://6.6.6.32:3000/";
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;

    public  static User currentUser;
    public  static Restaurant currentRestaurant;
    public static Set<Addon> addonList = new HashSet<>();



}
