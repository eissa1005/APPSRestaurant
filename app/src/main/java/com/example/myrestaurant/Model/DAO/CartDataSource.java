package com.example.myrestaurant.Model.DAO;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CartDataSource {

    Flowable<List<CartItem>> getAllCart(int fbid, int restaurantId);

    Single<Integer> countItemInCart(int fbid, int restaurantId);

    Single<Long> sumPrice(int fbid, int restaurantId);

    Single<CartItem> getItemInCart(String foodId, int fbid, int restaurantId);

    Completable insertOrReplaceAll(CartItem... cartItems);

    Single<Integer> updateCart(CartItem cart);

    Single<Integer> deleteCart(CartItem cart);

    Single<Integer> cleanCart(int fbid, int restaurantId);

}
