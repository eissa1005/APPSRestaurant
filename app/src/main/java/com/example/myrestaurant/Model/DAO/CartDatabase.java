package com.example.myrestaurant.Model.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities =CartItem.class,version = 2,exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
     public abstract CartDAO cartDAO();

     private static CartDatabase cartInstance;
     private static final String dbName="DbRestaurant";
     public static CartDatabase getInstance(Context context){
         if(cartInstance == null){
             cartInstance = Room.databaseBuilder(context,CartDatabase.class,dbName)
                     .fallbackToDestructiveMigration()
                     .build();
         }
         return cartInstance;
     }



}
