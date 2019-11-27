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

    @Query("SELECT * FROM Cart WHERE fbid=:fbid AND restaurantId=:restaurantId")
    Flowable<List<CartItem>> getAllCart(String fbid,int restaurantId );

    @Query("SELECT COUNT(*) FROM Cart WHERE fbid=:fbid AND restaurantId=:restaurantId")
    Single<Integer> countItemInCart(String fbid,int restaurantId );

    // Total Value Sum(Price*QTY)
    @Query("SELECT SUM(foodPrice*foodQuantity) FROM Cart WHERE fbid=:fbid AND restaurantId=:restaurantId")
    Single<Long> sumPrice(String fbid, int restaurantId);

    @Query("SELECT * FROM Cart WHERE foodId=:foodId AND fbid=:fbid AND restaurantId=:restaurantId")
    Single<CartItem> getItemInCart(String foodId, String fbid, int restaurantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrReplaceAll(CartItem...cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCart(CartItem cart);

    @Delete
    Single<Integer> deleteCart(CartItem cart);

    @Query("DELETE FROM Cart WHERE fbid=:fbid AND restaurantId=:restaurantId")
    Single<Integer> cleanCart(String fbid, int restaurantId);
}
