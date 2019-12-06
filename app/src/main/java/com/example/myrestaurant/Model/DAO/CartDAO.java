package com.example.myrestaurant.Model.DAO;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {

    @Query("SELECT * FROM Cart WHERE FBID=:FBID AND RestaurantId=:restaurantId")
    Flowable<List<CartItem>> getAllCart(String FBID,int restaurantId );

    @Query("SELECT COUNT(*) FROM Cart WHERE FBID=:FBID AND RestaurantId=:restaurantId")
    Single<Integer> countItemInCart(String FBID,int restaurantId );

    // Total Value Sum(Price*QTY)
    @Query("SELECT SUM(foodPrice*foodQuantity) FROM Cart WHERE FBID=:FBID AND RestaurantId=:restaurantId")
    Single<Long> sumPrice(String FBID, int restaurantId);

    @Query("SELECT * FROM Cart WHERE FoodId=:foodId AND FBID=:FBID AND RestaurantId=:restaurantId")
    Single<CartItem> getItemInCart(int foodId, String FBID, int restaurantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrReplaceAll(CartItem...cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCart(CartItem cart);

    @Delete
    Single<Integer> deleteCart(CartItem cart);

    @Query("DELETE FROM Cart WHERE FBID=:FBID AND RestaurantId=:restaurantId")
    Single<Integer> cleanCart(String FBID, int restaurantId);
}
